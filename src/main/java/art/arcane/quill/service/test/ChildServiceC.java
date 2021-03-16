package art.arcane.quill.service.test;

import art.arcane.quill.logging.L;
import art.arcane.quill.service.QuillService;

public class ChildServiceC extends QuillService
{
    private int c = 0;
    @Override
    public void onEnable() {
        v("My Parent is " + getParent().getServiceName());
    }

    @Override
    public void onDisable() {
    }
}
