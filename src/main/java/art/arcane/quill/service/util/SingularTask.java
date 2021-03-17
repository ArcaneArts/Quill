package art.arcane.quill.service.util;

import art.arcane.quill.Quill;
import art.arcane.quill.collections.functional.NastyRunnable;
import art.arcane.quill.logging.L;
import art.arcane.quill.math.M;
import art.arcane.quill.service.services.SchedulerService;
import lombok.Getter;

public class SingularTask implements ITask
{
    @Getter
    private transient final long dueDate;

    @Getter
    private transient final int taskId;
    private transient final NastyRunnable runnable;

    private SingularTask(long dueDate, NastyRunnable runnable)
    {
        this.taskId = SchedulerService.nextTaskId();
        this.dueDate = dueDate;
        this.runnable = runnable;
    }

    public static SingularTask at(long dueDate, NastyRunnable runnable)
    {
        return new SingularTask(dueDate, runnable);
    }

    public static SingularTask after(long delay, NastyRunnable runnable)
    {
        return at(M.ms() + delay, runnable);
    }

    public static SingularTask now(NastyRunnable runnable)
    {
        return at(0, runnable);
    }

    @Override
    public void run() throws Throwable{
        runnable.run();
    }
}
