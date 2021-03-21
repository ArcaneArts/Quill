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

package art.arcane.quill.service.util;

import art.arcane.quill.collections.functional.NastyRunnable;
import art.arcane.quill.math.M;
import art.arcane.quill.service.services.SchedulerService;
import lombok.Getter;

public class RepeatingTask implements ITask, ICancellableTask {
    @Getter
    private transient final int taskId;
    private transient final long interval;
    private transient final NastyRunnable runnable;
    @Getter
    private transient long dueDate;
    private transient boolean cancelled;

    private RepeatingTask(long dueDate, NastyRunnable runnable, long interval) {
        this.cancelled = false;
        this.taskId = SchedulerService.nextTaskId();
        this.dueDate = dueDate;
        this.runnable = runnable;
        this.interval = interval;
    }

    public static RepeatingTask at(long dueDate, NastyRunnable runnable, long interval) {
        return new RepeatingTask(dueDate, runnable, interval);
    }

    public static RepeatingTask after(long delay, NastyRunnable runnable, long interval) {
        return at(M.ms() + delay, runnable, interval);
    }

    public static RepeatingTask now(NastyRunnable runnable, long interval) {
        return at(0, runnable, interval);
    }

    @Override
    public void run() throws Throwable {
        Throwable rethrow = null;

        try {
            runnable.run();
        } catch (Throwable e) {
            rethrow = e;
        }

        dueDate = M.ms() + interval;

        if (rethrow != null) {
            throw rethrow;
        }
    }

    @Override
    public boolean shouldRequeue() {
        return !cancelled;
    }

    @Override
    public void cancel() {
        cancelled = true;
    }
}
