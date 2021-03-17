package art.arcane.quill.service.services;

import art.arcane.quill.Quill;
import art.arcane.quill.collections.KList;
import art.arcane.quill.execution.J;
import art.arcane.quill.logging.L;
import art.arcane.quill.math.M;
import art.arcane.quill.math.Profiler;
import art.arcane.quill.service.QuillService;
import art.arcane.quill.service.util.ITask;
import lombok.*;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class SchedulerService extends QuillService {
    private static int taskIds = 0;
    private transient final KList<ITask> tasks = new KList<>();
    private transient final KList<Integer> dequeueIndex = new KList<>();
    private transient final Thread schedulerThread = new Thread(J.runWhile(this::isEnabled, this::tickScheduler));
    private transient final AtomicLong lastTick = new AtomicLong(M.ms());
    private transient final AtomicLong tickDeviation = new AtomicLong(0);
    private transient final AtomicInteger tickCount = new AtomicInteger(Integer.MIN_VALUE);
    private transient final AtomicBoolean lastTickOverflow = new AtomicBoolean(false);
    private transient final Profiler executionTimer = new Profiler();

    /**
     * The interval at which this scheduler ticks at. 100 = 10/s. 50 = 20/s.
     */
    @Builder.Default
    private long tickInterval = 100;

    /**
     * The thread priority to use [1-10] (5 = normal)
     */
    @Builder.Default
    private int schedulerThreadPriority = Thread.MIN_PRIORITY;

    @Builder.Default
    private boolean crashOnTaskException = true;

    /**
     * If smooth tickrate is enabled, this cheduler will attempt to execute about [tickInterval]
     * milliseconds worth of execution, then it will pause for the remaining duration of the tick.
     * This will distort the due dates of scheduled tasks, but it keeps the scheduler running
     * smoothly without choppy bursts of execution. Disable this if timing is important to this
     * sheduler. If smooth tick is disabled, all tasks will be executed every tick. The following
     * ticks will be delayed if there is more work than the specified tick interval.
     */
    @Builder.Default
    private boolean smoothTickrate = true;

    /**
     * If smooth tickrate is enabled, and each tick has to stop executing because it spent
     * more cpu time than was allowed for that tick, should this scheduler shuffle the task
     * queue to allow leftover tasks to get a chance. This prevents repeating tasks from
     * constantly stealing all the work time from singleton tasks.
     *
     * Reshuffling is scheduled to happen if a tick uses more than 95% of it's allowed work time
     */
    @Builder.Default
    private boolean reshuffleWhenPegged = true;

    /**
     * If smooth ticking is enabled and ticks are taking longer than the rate,
     * when ticks stop consuming too much time finally, should we fast-foward ticks to
     * resync to where we would have been. This means after X amount of time, repeating
     * tasks would generally be called the amount of times they should have been called without
     * tick lag.
     */
    @Builder.Default
    private boolean synchronize = true;

    /**
     * If reshuffle is enabled, how often (in ticks) can the queue be reshuffled.
     */
    @Builder.Default
    private int reshuffleInterval = 5;

    /**
     * Smooth tickrate is disabled if there are more than the specified amount queued tasks.
     */
    @Builder.Default
    private int burstExecutionThreshold = 1000;

    @Override
    public void onEnable() {
        schedulerThread.setName(getParent().getServiceName() + " Scheduler");
        schedulerThread.setPriority(getSchedulerThreadPriority());
        schedulerThread.start();
    }

    /**
     * Queue a task to be executed. You can use SingularTask, or RepeatingTask.
     * Execution is sensitive to ICancellableTask. This will allow that task to be
     * cancelled by it's id.
     * @param task the task to queue
     */
    public synchronized void queue(ITask task)
    {
        tasks.add(task);
    }

    private synchronized void tickScheduler()
    {
        long spent = execute(getTickInterval());
        tickDeviation.addAndGet(spent - getTickInterval());

        if(isSynchronize())
        {
            while(getTickDeviation().get() > getTickInterval() && spent < getTickInterval())
            {
                long ffspent = execute(getTickInterval());
                getTickDeviation().addAndGet(ffspent - getTickInterval());
                spent += ffspent;
            }

            if(getTickDeviation().get() < -getTickInterval())
            {
                J.sleep(getTickInterval());
                getTickDeviation().addAndGet(-getTickInterval());
            }
        }

        if(spent < getTickInterval())
        {
            J.sleep(getTickInterval() - spent);
        }
    }

    private synchronized long execute(long maxTime) {
        executionTimer.begin();
        dequeueIndex.clear();
        int t = tickCount.getAndIncrement();
        long time = M.ms();

        if(isReshuffleWhenPegged() && lastTickOverflow.get() && t % getReshuffleInterval() == 0)
        {
            lastTickOverflow.set(false);
            tasks.shuffle();
        }

        for(int i = 0; i < tasks.size(); i++)
        {
            ITask task = tasks.get(i);

            if(task.getDueDate() <= time)
            {
                executeTask(task);

                if(!task.shouldRequeue())
                {
                    dequeueIndex.add(i);
                }
            }

            if((isSmoothTickrate() && !(tasks.size() > getBurstExecutionThreshold())) && executionTimer.getMilliseconds() >= maxTime)
            {
                lastTickOverflow.set(true);
                break;
            }
        }

        for(int i = dequeueIndex.size()-1; i >= 0; i--)
        {
            tasks.remove(i);
        }

        lastTick.set(time);
        return (long) Math.ceil(executionTimer.getMilliseconds());
    }

    private void executeTask(ITask task) {
        try
        {
            task.run();
        }

        catch (Throwable e)
        {
            L.f("Task Exception Encountered in " + getSchedulerThread().getName());
            L.ex(e);

            if(isCrashOnTaskException())
            {
                Quill.crashStack("Task Exception Encountered in " + getSchedulerThread().getName() + " (See exception above)");
            }
        }
    }

    @Override
    public void onDisable() {

    }

    public static synchronized int nextTaskId()
    {
        return taskIds++;
    }
}
