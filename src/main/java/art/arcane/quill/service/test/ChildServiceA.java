package art.arcane.quill.service.test;

import art.arcane.quill.service.QuillService;
import art.arcane.quill.service.Service;

public class ChildServiceA extends QuillService
{
    @Service
private ChildServiceB b = new ChildServiceB();
    private int a = 0;
    @Override
    public void onEnable() {
        v("My Parent is " + getParent().getServiceName());
        w("This is warning");
        f("This is error");
    }

    @Override
    public void onDisable() {
    }
}
