package art.arcane.quill.service;

import art.arcane.quill.Quill;
import art.arcane.quill.logging.L;

public class TestService extends QuillService
{
    public static void main(String[] a)
    {
        Quill.start(a);
    }

    @ServiceWorker
    private TestServiceWorker testServiceWorker;

    public TestService() {
        super("TestSVC");
    }

    @Override
    public void onEnable() {
        L.v("Enabled!");
    }

    @Override
    public void onDisable() {
        L.v("Disabled!");
    }
}
