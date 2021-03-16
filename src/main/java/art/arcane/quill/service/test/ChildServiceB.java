package art.arcane.quill.service.test;

import art.arcane.quill.logging.L;
import art.arcane.quill.service.QuillService;
import art.arcane.quill.service.Service;

public class ChildServiceB extends QuillService
{ @Service
private ChildServiceC c = new ChildServiceC();
    private int b = 0;
    @Override
    public void onEnable() {
        v("My Parent is " + getParent().getServiceName());
    }

    @Override
    public void onDisable() {
    }
}
