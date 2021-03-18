package art.arcane.quill.service;

import art.arcane.quill.Quill;
import art.arcane.quill.collections.KList;
import art.arcane.quill.execution.J;
import art.arcane.quill.format.Form;
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
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Represents a basic service of Quill. Each JVM typically has one service.
 */
@Data
public abstract class QuillService implements IService {
    private transient final String serviceName;
    private transient final AtomicBoolean duringInit;
    private transient ExecutorService sparkplugs;
    private transient final InstanceCreator<IService> serviceInstanceCreator;
    private transient Gson configGson;
    private transient int serviceId = Quill.serviceIds++;
    private transient int serviceDepth = 0;
    private transient boolean enabled;
    private transient IService parent;

    public QuillService() {
        this.serviceName = getClass().getSimpleName().replaceAll("\\QService\\E", "").replaceAll("\\QWorker\\E", "").replaceAll("\\QSVC\\E", "");
        sparkplugs = null;
        this.enabled = false;
        serviceInstanceCreator = type -> this;
        setServiceDepth(0);
        duringInit = new AtomicBoolean(false);
    }

    private static String toString(Object... l)
    {
        if(l.length == 1)
        {
            return (l[0] != null ? l[0].toString() : "null");
        }

        StringBuilder sb = new StringBuilder();

        for(Object i : l)
        {
            sb.append(i == null ? "null" : i.toString());
            sb.append(" ");
        }

        return sb.toString();
    }

    public void i(Object...v)
    {
        L.i((duringInit.get() ? Form.repeat("  ", serviceDepth) : "") + "〘" + getServiceName() + "〙 " + toString(v));
    }

    public void v(Object...v)
    {
        L.v((duringInit.get() ? Form.repeat("  ", serviceDepth) : "") + "〘" + getServiceName() + "〙 " + toString(v));
    }

    public void w(Object...v)
    {
        L.w((duringInit.get() ? Form.repeat("  ", serviceDepth) : "") + "〘" + getServiceName() + "〙 " + toString(v));
    }

    public void f(Object...v)
    {
        L.f((duringInit.get() ? Form.repeat("  ", serviceDepth) : "") + "〘" + getServiceName() + "〙 " + toString(v));
    }

    public IService getRawService(String field) {
        try {
            Field f = getClass().getDeclaredField(field);
            f.setAccessible(true);
            return (IService) f.get(this);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            L.ex(e);
            Quill.crash("Failed to obtain subservice worker in service worker");
        }

        return null;
    }

    public KList<IService> getChildServices()
    {
        KList<IService> qsw = new KList<>();

        for (Field i : getAllFields(getClass())) {
            try {
                if (i.isAnnotationPresent(Service.class)) {
                    i.setAccessible(true);
                    IService sw = (IService) i.get(this);

                    if(sw != null)
                    {
                        qsw.add(sw);
                    }
                }
            } catch (Throwable ignored) {


            }
        }

        return qsw;
    }

    public boolean hasParent() {
        return getParent() != null;
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    public void start() {
        Quill.logState("service[" + getServiceName() + "]", "startup");
        if (enabled) {
            Quill.crashStack("Service Worker " + getName() + " was already running!");
        }

        try {
            L.i(Form.repeat("  ", serviceDepth) + "Starting " + getName() + " Service Worker");

            Quill.logState("service[" + getServiceName() + "]", "startup.startChildren");
            for (Field i : getAllFields(getClass())) {
                enableServiceWorker(i);
            }

            duringInit.set(true);
            Quill.logState("service[" + getServiceName() + "]", "startup.onEnable");
            onEnable();
            duringInit.set(false);
            L.i(Form.repeat("  ", serviceDepth) + "Started " + getName() + " Service Worker");
            enabled = true;
            Quill.logState("service[" + getServiceName() + "]", "online");
        } catch (Throwable e) {
            L.ex(e);
            Quill.crash("Failed to enable " + getName());
        }
    }

    public void stop() {
        if (!enabled) {
            return;
        }

        try {
            L.i(Form.repeat("  ", serviceDepth) + "Stopping " + getName() + " Service Worker");
            onDisable();

            for (Field i : getAllFields(getClass())) {
                disableServiceWorker(i);
            }

            L.i(Form.repeat("  ", serviceDepth) + "Stopped " + getName() + " Service Worker");
            enabled = false;
        } catch (Throwable e) {
            L.ex(e);
            Quill.crash("Failed to disable " + getName());
        }
    }

    protected void enableServiceWorker(Field i) {
        enableServiceWorker(i, this);
    }

    protected void enableServiceWorker(Field i, Object o) {
        if (i.isAnnotationPresent(Service.class)) {

            i.setAccessible(true);
            Class<? extends IService> worker = (Class<? extends IService>) i.getType();

            try {
                IService sw = (IService) i.get(o);

                if(sw == null)
                {
                    sw = (IService) i.getType().getConstructor().newInstance();
                    i.set(o, sw);
                }

                Quill.logState("service[" + getServiceName() + "]", "children." + sw.getServiceName() + ".init");
                sw.setServiceDepth(getServiceDepth() + 1);
                sw.setParent(this);
                try {
                    Quill.logState("service[" + getServiceName() + "]", "children." + sw.getServiceName() + ".starting");
                    sw.start();
                } catch (Throwable ex) {
                    L.ex(ex);
                    Quill.crash("Failed to enable service worker " + worker.getCanonicalName());
                }

                Quill.logState("service[" + getServiceName() + "]", "children." + sw.getServiceName() + ".online");
            } catch (Throwable e) {
                L.ex(e);
                Quill.crash("Failed to initialize service worker " + worker.getCanonicalName());
            }
        }
    }

    protected void disableServiceWorker(Field i) {
        disableServiceWorker(i, this);
    }

    private void disableServiceWorker(Field i, Object o) {
        if (i.isAnnotationPresent(Service.class)) {
            i.setAccessible(true);
            Class<? extends IService> worker = (Class<? extends IService>) i.getType();

            try {
                IService sw = (IService) i.get(o);

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

    public abstract void onEnable();

    public abstract void onDisable();

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
        Thread.currentThread().setName(getServiceName());
        L.i("Starting " + getServiceName() + " Service");
        configGson = new GsonBuilder()
                .registerTypeAdapter(Quill.delegateClass, serviceInstanceCreator)
                .create();
        sparkplugs = Executors.newCachedThreadPool(new ThreadFactory() {
            int tid = 0;

            @Override
            public Thread newThread(Runnable r) {
                tid++;
                Thread t = new Thread(r);
                t.setName(getServiceName() + " Sparkplug " + tid);
                t.setPriority(Thread.MAX_PRIORITY);
                t.setUncaughtExceptionHandler((et, e) ->
                {
                    L.ex(e);
                    Quill.crashStack("Exception encountered in " + et.getName());
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
        Quill.runPost();
        L.i("" + getServiceName() + " Service has Started");
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
        IService delegateDummy = delegateClass.getConstructor().newInstance();
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
        L.i("Stopping " + getServiceName() + " Service");
        onDisable();

        for (Field i : getAllFields(getClass())) {
            disableServiceWorker(i);
        }

        L.i("" + getServiceName() + " Service has Stopped");
    }

    protected static List<Field> getAllFields(Class<?> aClass) {
        List<Field> fields = new ArrayList<>();
        do {
            Collections.addAll(fields, aClass.getDeclaredFields());
            aClass = aClass.getSuperclass();
        } while (aClass != null);
        return fields;
    }
}
