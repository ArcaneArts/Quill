package art.arcane.quill.service;

public interface IService {
    public void start();

    public void stop();

    public void onEnable();

    public void onDisable();

    public void setParent(IService quillService);

    public void setServiceDepth(int i);

    String getServiceName();
}
