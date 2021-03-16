package art.arcane.quill.service;

import art.arcane.quill.Quill;
import art.arcane.quill.collections.KList;
import art.arcane.quill.format.Form;
import art.arcane.quill.logging.L;
import com.google.gson.InstanceCreator;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class QuillServiceWorker {
    @Getter
    @Setter
    private transient int serviceDepth = 0;

    @Getter
    private transient boolean enabled;

    @Getter
    @Setter
    private transient QuillServiceWorker parent = null;

    @Getter
    private transient final InstanceCreator<QuillServiceWorker> instanceCreator;

    public QuillServiceWorker() {
        instanceCreator = type -> this;
        this.enabled = false;
    }

    /**
     * Recursivley walks up the service tree this worker is branched on, attempting to force-cast every service field to the return result (generic t)
     * @param <T> A service worker
     * @return The first found service
     */
    public <T extends QuillServiceWorker> T firstParentService()
    {
        return firstParentService(false);
    }

    /**
     * Recursivley walks up the service tree this worker is branched on, attempting to force-cast every service field to the return result (generic t)
     * @param <T> A service worker
     * @param debug If enabled failed results will print their search paths (defaults to false). Creates a lot of garbage if used for everything so be aware.
     * @return The first found service
     */
    public <T extends QuillServiceWorker> T firstParentService(boolean debug)
    {
        KList<String> v = debug ? new KList<>() : null;
        return firstParentService(v);
    }

    public <T extends QuillServiceWorker> T firstParentService(KList<String> debugging)
    {
        for(QuillServiceWorker i : getChildServices())
        {
            try
            {
                return (T) i;
            }

            catch(Throwable ignored)
            {
                if(debugging != null)
                {
                    debugging.add("? " + i.getName() + " (" + i.getClass().getSimpleName() + ")");
                }
            }
        }

        if(hasParent())
        {
            if(debugging != null)
            {
                debugging.add("? /\\ (" + getParent().getName() + " (" + getParent().getClass().getSimpleName() + "))");
            }

            return getParent().firstParentService(debugging);
        }

        if(debugging != null)
        {
            L.f("=================================================");
            L.flush();
            L.f("Unable to find parent worker");
            L.f("SEARCH PATH: ");
            debugging.forEach((i) -> L.f("  " + i));
            L.f("=================================================");
            L.flush();
        }

        return null;
    }

    /**
     * Recursivley walks down the service tree this worker is branched on, attempting to force-cast every service field to the return result (generic t)
     * @param <T> A service worker
     * @return The first found service
     */
    public <T extends QuillServiceWorker> T firstChildService()
    {
        for(QuillServiceWorker i : getChildServices())
        {
            try
            {
                return (T) i;
            }

            catch(Throwable ignored)
            {

            }
        }

        for(QuillServiceWorker i : getChildServices())
        {
            try
            {
                T t = i.firstChildService();

                if(t != null)
                {
                    return t;
                }
            }

            catch(Throwable ignored)
            {

            }
        }

        return null;
    }

    public QuillServiceWorker getRawService(String field) {
        try {
            Field f = getClass().getDeclaredField(field);
            f.setAccessible(true);
            return (QuillServiceWorker) f.get(this);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            L.ex(e);
            Quill.crash("Failed to obtain subservice worker in service worker");
        }

        return null;
    }

    public KList<QuillServiceWorker> getChildServices()
    {
        KList<QuillServiceWorker> qsw = new KList<>();

        for (Field i : getAllFields(getClass())) {
            try {
                if (i.isAnnotationPresent(ServiceWorker.class)) {
                    i.setAccessible(true);
                    QuillServiceWorker sw = (QuillServiceWorker) i.get(this);

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
        if (enabled) {
            Quill.crashStack("Service Worker " + getName() + " was already running!");
        }

        try {
            L.v(Form.repeat("  ", serviceDepth) + "Starting " + getName() + " Service Worker");

            for (Field i : getAllFields(getClass())) {
                enableServiceWorker(i);
            }

            onEnable();
            L.l(Form.repeat("  ", serviceDepth) + "Started " + getName() + " Service Worker");
            enabled = true;
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
            L.v(Form.repeat("  ", serviceDepth) + "Stopping " + getName() + " Service Worker");
            onDisable();

            for (Field i : getAllFields(getClass())) {
                disableServiceWorker(i);
            }

            L.l(Form.repeat("  ", serviceDepth) + "Stopped " + getName() + " Service Worker");
            enabled = false;
        } catch (Throwable e) {
            L.ex(e);
            Quill.crash("Failed to disable " + getName());
        }
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
                sw.setServiceDepth(getServiceDepth() + 1);
                sw.setParent(this);
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

    private static List<Field> getAllFields(Class<?> aClass) {
        List<Field> fields = new ArrayList<>();
        do {
            Collections.addAll(fields, aClass.getDeclaredFields());
            aClass = aClass.getSuperclass();
        } while (aClass != null);
        return fields;
    }

    public abstract void onEnable();

    public abstract void onDisable();
}
