package art.arcane.quill.service;

import art.arcane.quill.Quill;
import art.arcane.quill.collections.KList;
import art.arcane.quill.logging.L;

public class TestService extends QuillService
{
    public static void main(String[] a)
    {
        Quill.start(a);
    }

    @ServiceWorker
    private TestServiceWorker testServiceWorker;

    @ServiceWorker
    private ConsoleServiceWorker console;

    public TestService() {
        super("TestSVC");
    }

    @Override
    public void onEnable() {
        console.registerCommand("foo", params -> {
            L.v("Bar!");
            // TRUE = handled, FALSE = try another command (for duplicate names)
            return true;
        });

        console.registerCommand("echo", params -> {
            L.v(KList.from(params).toString(" "));
            return true;
        });
    }

    @Override
    public void onDisable() {
        L.v("Disabled!");
    }
}
