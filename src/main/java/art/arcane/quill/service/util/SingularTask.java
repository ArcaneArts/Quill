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

public class SingularTask implements ITask {
    @Getter
    private transient final long dueDate;

    @Getter
    private transient final int taskId;
    private transient final NastyRunnable runnable;

    private SingularTask(long dueDate, NastyRunnable runnable) {
        this.taskId = SchedulerService.nextTaskId();
        this.dueDate = dueDate;
        this.runnable = runnable;
    }

    public static SingularTask at(long dueDate, NastyRunnable runnable) {
        return new SingularTask(dueDate, runnable);
    }

    public static SingularTask after(long delay, NastyRunnable runnable) {
        return at(M.ms() + delay, runnable);
    }

    public static SingularTask now(NastyRunnable runnable) {
        return at(0, runnable);
    }

    @Override
    public void run() throws Throwable {
        runnable.run();
    }
}
