package art.arcane.quill.service.test;

import art.arcane.quill.Quill;
import art.arcane.quill.collections.KList;
import art.arcane.quill.logging.L;
import art.arcane.quill.service.services.ConsoleService;
import art.arcane.quill.service.QuillService;
import art.arcane.quill.service.Service;

public class TestService extends QuillService
{
    private int vvv = 0;
    public static void main(String[] a)
    {
        Quill.start(a);
    }

    @Service
    private ConsoleService console;

    @Service
    private ChildServiceA a = new ChildServiceA();

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
