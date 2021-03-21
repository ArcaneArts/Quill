/*
 * This file is part of Quill by Arcane Arts.
 *
 * Quill by Arcane Arts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * Quill by Arcane Arts is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License in this package for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Quill.  If not, see <https://www.gnu.org/licenses/>.
 */

package art.arcane.quill.service.services;

import art.arcane.quill.Quill;
import art.arcane.quill.collections.KList;
import art.arcane.quill.collections.functional.NastyRunnable;
import art.arcane.quill.execution.ChronoLatch;
import art.arcane.quill.execution.J;
import art.arcane.quill.format.Form;
import art.arcane.quill.logging.L;
import art.arcane.quill.math.M;
import art.arcane.quill.math.Profiler;
import art.arcane.quill.service.QuillService;
import art.arcane.quill.service.util.ITask;
import art.arcane.quill.service.util.SingularTask;
import lombok.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchedulerService extends QuillService {
    private static int taskIds = 0;
    private transient final KList<ITask> tasks = new KList<>();
    private transient final AtomicLong lastTick = new AtomicLong(M.ms());
    private transient final AtomicLong tickDeviation = new AtomicLong(0);
    private transient final AtomicInteger tickCount = new AtomicInteger(Integer.MIN_VALUE);
    private transient final AtomicBoolean lastTickOverflow = new AtomicBoolean(false);
    private transient final Profiler executionTimer = new Profiler();
    private transient final ChronoLatch lagWarn = new ChronoLatch(10000);
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
     * <p>
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
     * Warn when the tick deviation is lagging further than the specified amount of time
     */
    @Builder.Default
    private long lagWarning = 1000;
    /**
     * Warn when the scheduler has to burst
     */
    @Builder.Default
    private boolean burstWarning = true;
    /**
     * Warn when a task takes longer than the specified ticks
     */
    @Builder.Default
    private long bigTaskWarning = 10;
    /**
     * Announce each tick and it's sync status
     */
    @Builder.Default
    private boolean verbose = false;
    /**
     * If reshuffle is enabled, how often (in ticks) can the queue be reshuffled.
     */
    @Builder.Default
    private int reshuffleInterval = 5;
    /**
     * Smooth tickrate is disabled if there are more than the specified amount queued tasks.
     */
    @Builder.Default
    private int burstExecutionThreshold = 1024;
    private transient final Thread schedulerThread = new Thread(J.runWhile(this::isEnabled, this::tickScheduler));

    public static synchronized int nextTaskId() {
        return taskIds++;
    }

    @Override
    public void onEnable() {
        schedulerThread.setName(getParent().getServiceName() + " Scheduler");
        schedulerThread.setPriority(getSchedulerThreadPriority());
        Quill.postJob(schedulerThread::start);
    }

    public Future<Void> whenCompleted() {
        return CompletableFuture.runAsync(() -> J.sleepWhile(() -> {
            L.v("Tasks = " + tasks.size());
            return tasks.size() > 0;
        }));
    }

    /**
     * Queue a task to be executed. You can use SingularTask, or RepeatingTask.
     * Execution is sensitive to ICancellableTask. This will allow that task to be
     * cancelled by it's id.
     *
     * @param task the task to queue
     */
    public int queue(ITask task) {
        synchronized (tasks) {
            tasks.add(task);

            return task.getTaskId();
        }
    }

    public boolean cancelTask(int taskId) {
        synchronized (tasks) {
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).getTaskId() == taskId) {
                    tasks.remove(i);
                    return true;
                }
            }

            return false;
        }
    }

    public void queue(NastyRunnable r) {
        queue(SingularTask.now(r));
    }

    public void queue(long delay, NastyRunnable r) {
        queue(SingularTask.after(delay, r));
    }

    private void tickScheduler() {
        long spent = execute(getTickInterval());
        getTickDeviation().addAndGet(spent - getTickInterval());

        if (isSynchronize()) {
            if (getTickDeviation().get() > getTickInterval() * lagWarning && lagWarn.flip()) {
                w("Can't keep up! " + Form.duration(getTickDeviation().get(), 1) + " Behind Schedule!");
            }

            while (getTickDeviation().get() > getTickInterval() && spent < getTickInterval()) {
                long ffspent = execute(getTickInterval());
                getTickDeviation().addAndGet(ffspent - getTickInterval());
                spent += ffspent;
            }

            while (getTickDeviation().get() < -(getTickInterval() + 100)) {
                J.sleep(getTickInterval());
                getTickDeviation().addAndGet(getTickInterval());
            }
        }

        if (spent < getTickInterval()) {
            J.sleep(getTickInterval() - spent);
        }

        if (isVerbose()) {
            v("TASK: " + Form.f(tasks.size()) + " LAG: " + (tickDeviation.get() > 0 ? (Form.duration(tickDeviation.get(), 0) + " Behind") : (Form.duration(-tickDeviation.get(), 0) + " Ahead")) + " EFFORT: " + Form.pc(spent / (double) getTickInterval(), 0));
        }
    }

    private long execute(long maxTime) {
        executionTimer.begin();
        int t = tickCount.getAndIncrement();
        long time = M.ms();

        synchronized (tasks) {
            if (isReshuffleWhenPegged() && lastTickOverflow.get() && t % getReshuffleInterval() == 0) {
                lastTickOverflow.set(false);
                tasks.shuffle();
            }

            if (tasks.size() > getBurstExecutionThreshold() && (tasks.size() - getBurstExecutionThreshold()) > 5) {
                w("Queue Overflow! Executing " + (tasks.size() - getBurstExecutionThreshold()) + " tasks next tick!");
            }

            for (int i = tasks.size() - 1; i >= 0; i--) {
                ITask task = tasks.get(i);

                if (task.getDueDate() <= time) {
                    double took = executionTimer.getMilliseconds();
                    executeTask(task);

                    if (executionTimer.getMilliseconds() - took > (bigTaskWarning * getTickInterval())) {
                        w("Task #" + task.getTaskId() + " took too long (" + Form.duration(executionTimer.getMilliseconds() - took, 1) + ")");
                    }

                    if (!task.shouldRequeue()) {
                        tasks.remove(i);
                    }
                }

                if ((isSmoothTickrate() && !(tasks.size() > getBurstExecutionThreshold())) && executionTimer.getMilliseconds() >= maxTime) {
                    lastTickOverflow.set(true);
                    break;
                }
            }
        }

        lastTick.set(time);
        return (long) Math.ceil(executionTimer.getMilliseconds());
    }

    private void executeTask(ITask task) {
        try {
            task.run();
        } catch (Throwable e) {
            L.f("Task Exception Encountered in " + getSchedulerThread().getName());
            L.ex(e);

            if (isCrashOnTaskException()) {
                Quill.crashStack("Task Exception Encountered in " + getSchedulerThread().getName() + " (See exception above)");
            }
        }
    }

    @Override
    public void onDisable() {

    }
}
