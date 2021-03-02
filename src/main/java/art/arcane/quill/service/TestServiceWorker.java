package art.arcane.quill.service;

import art.arcane.quill.logging.L;

public class TestServiceWorker extends QuillServiceWorker {
    @Override
    public void onEnable() {
        L.v("Worker Enabled!");
    }

    @Override
    public void onDisable() {
        L.v("Worker Disabled!");
    }
}
