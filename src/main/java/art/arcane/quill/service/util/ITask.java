package art.arcane.quill.service.util;

public interface ITask {
    public long getDueDate();

    public void run() throws Throwable;

    public int getTaskId();

    public default boolean shouldRequeue()
    {
        return false;
    }
}
