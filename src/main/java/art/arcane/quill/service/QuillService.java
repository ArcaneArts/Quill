package art.arcane.quill.service;

import art.arcane.quill.Quill;
import art.arcane.quill.collections.KList;
import art.arcane.quill.collections.KMap;
import art.arcane.quill.execution.J;
import art.arcane.quill.io.IO;
import art.arcane.quill.json.JSONObject;
import art.arcane.quill.logging.L;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import lombok.Data;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Represents a basic service of Quill. Each JVM typically has one service.
 */
@Data
public abstract class QuillService {
    private transient final String serviceName;
    private transient ExecutorService sparkplugs;
    private transient boolean failing;
    private transient final InstanceCreator<QuillService> instanceCreator;
    private transient final Gson configGson;

    /**
     * A service must have a service name (capitalization is suggested)
     *
     * @param serviceName the fancy service name that will run on this jvm
     */
    public QuillService(String serviceName) {
        //@builder
        this.serviceName = serviceName;
        Thread.currentThread().setName("Quill " + getServiceName());
        sparkplugs = null;
        failing = false;
        instanceCreator = type -> this;
        KMap<Class<?>, InstanceCreator<?>> c = new KMap<>();

        configGson = new GsonBuilder()
                .registerTypeAdapter(Quill.delegateClass, instanceCreator)
                .create();
        //@done
    }

    /**
     * Queue a task to be run in parallel with other async init jobs. This service wont be considered online until these tasks finish
     *
     * @param r the runnable to execute before startup completes
     */
    protected void asyncInit(Runnable r) {
        if (sparkplugs == null) {
            throw new RuntimeException("You can only use asyncInit while in onEnable");
        }

        sparkplugs.submit(r);
    }

    /**
     * Called by Quill service management. Do not call this.
     */
    public void startService() {
        L.i("Starting Quill" + getServiceName() + " Service");
        sparkplugs = Executors.newCachedThreadPool(new ThreadFactory() {
            int tid = 0;

            @Override
            public Thread newThread(Runnable r) {
                tid++;
                Thread t = new Thread(r);
                t.setName("Quill Sparkplug " + tid);
                t.setPriority(Thread.MAX_PRIORITY);
                t.setUncaughtExceptionHandler((et, e) ->
                {
                    L.f("Exception encountered in " + et.getName());
                    fail(e);
                });

                return t;
            }
        });

        for (Field i : new KList<>(getAllFields(getClass())).reverse()) {
            enableServiceWorker(i);
        }

        onEnable();
        sparkplugs.shutdown();

        try {
            sparkplugs.awaitTermination(30, TimeUnit.SECONDS);
        } catch (Throwable e) {
            L.f("Waited a full minute, can't shut down the thread pool. Skipping...");
        }

        sparkplugs = null;
        L.i("Quill" + getServiceName() + " Service has Started");
        Runtime.getRuntime().addShutdownHook(new Thread(this::stopService));
    }

    /**
     * Called by Quill service management. Do not call this as a service
     *
     * @param delegateClass the delegated Quill service
     * @return the configured service (from json)
     * @throws NoSuchMethodException     shit happens
     * @throws IllegalAccessException    shit happens
     * @throws InvocationTargetException shit happens
     * @throws InstantiationException    shit happens
     */
    public static QuillService initializeConfigured(Class<? extends QuillService> delegateClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        QuillService delegateDummy = delegateClass.getConstructor().newInstance();
        File configFile = new File("config/" + delegateDummy.getServiceName().toLowerCase() + ".json");
        configFile.getParentFile().mkdirs();
        JSONObject defaultConfig = new JSONObject(new Gson().toJson(delegateDummy));
        JSONObject currentConfig = new JSONObject();

        if (configFile.exists()) {
            try {
                currentConfig = new JSONObject(IO.readAll(configFile));
            } catch (Throwable e) {
                L.w("Failed to read config file. Regenerating...");
                L.ex(e);
            }
        }

        for (String i : defaultConfig.keySet()) {
            if (!currentConfig.has(i)) {
                L.v("Adding Default config value: " + i);
                currentConfig.put(i, defaultConfig.get(i));
            }
        }

        for (String i : currentConfig.keySet()) {
            if (!defaultConfig.has(i)) {
                L.w("Configuration key " + i + " is not reconized. Remove this from " + configFile.getPath());
            }
        }

        try {
            IO.writeAll(configFile, currentConfig.toString(4));
            L.v("Updated Configuration");
        } catch (Throwable e) {
            L.ex(e);
            Quill.crashStack("Failed to write a config file... This is bad");
            return null;
        }

        QuillService svc = new Gson().fromJson(currentConfig.toString(), delegateClass);
        L.i("Configuration Loaded");
        return svc;
    }

    /**
     * Called by the Quill service management. Do not call this. Instead use Quill.shutdown();
     */
    public void stopService() {
        L.i("Stopping Quill" + getServiceName() + " Service");
        onDisable();

        for (Field i : getAllFields(getClass())) {
            disableServiceWorker(i);
        }

        L.i("Quill" + getServiceName() + " Service has Stopped");
    }

    private void enableServiceWorker(Field i) {
        enableServiceWorker(i, this);
    }

    private void enableServiceWorker(Field i, Object o) {
        if (i.isAnnotationPresent(ServiceWorker.class)) {
            i.setAccessible(true);
            Class<? extends QuillServiceWorker> worker = (Class<? extends QuillServiceWorker>) i.getType();

            try {
                QuillServiceWorker sw = (QuillServiceWorker) i.get(o);

                if(sw == null)
                {
                    sw = (QuillServiceWorker) i.getType().getConstructor().newInstance();
                    i.set(o, sw);
                }

                sw.setServiceDepth(1);
                try {
                    sw.start();
                } catch (Throwable ex) {
                    L.ex(ex);
                    Quill.crash("Failed to enable service worker " + worker.getCanonicalName());
                }
            } catch (Throwable e) {
                L.ex(e);
                Quill.crash("Failed to initialize service worker " + worker.getCanonicalName());
            }
        }
    }

    private void disableServiceWorker(Field i) {
        disableServiceWorker(i, this);
    }

    private void disableServiceWorker(Field i, Object o) {
        if (i.isAnnotationPresent(ServiceWorker.class)) {
            i.setAccessible(true);
            Class<? extends QuillServiceWorker> worker = (Class<? extends QuillServiceWorker>) i.getType();

            try {
                QuillServiceWorker sw = (QuillServiceWorker) i.get(o);

                try {
                    sw.stop();
                } catch (Throwable ex) {
                    L.ex(ex);
                    Quill.crash("Faield to disable service worker " + worker.getCanonicalName());
                }
            } catch (Throwable e) {
                L.ex(e);
                Quill.crash("Failed to stop service worker " + worker.getCanonicalName());
            }
        }
    }

    private void fail(Throwable e) {
        failing = true;
        L.f("Service CRASH!");
        L.ex(e);

        L.w("Attempting to disable if possible...");
        J.attempt(() -> onDisable());
    }

    /**
     * This is called after all of the sub-service workers have been enabled. As a service, this is the last stage to initialize your components that are not service workers. You can also use asyncInit(Runnable) to initialize in parallel.
     */
    public abstract void onEnable();

    /**
     * This is called BEFORE all sub-service workers have been disabled. As a service this is typically the first stage in shutdown.
     */
    public abstract void onDisable();

    protected static List<Field> getAllFields(Class<?> aClass) {
        List<Field> fields = new ArrayList<>();
        do {
            Collections.addAll(fields, aClass.getDeclaredFields());
            aClass = aClass.getSuperclass();
        } while (aClass != null);
        return fields;
    }
}
