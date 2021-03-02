package art.arcane.quill.service;

import art.arcane.quill.Quill;
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
