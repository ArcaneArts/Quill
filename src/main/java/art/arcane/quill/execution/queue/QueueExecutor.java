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

package art.arcane.quill.execution.queue;

import art.arcane.quill.execution.Looper;

public class QueueExecutor extends Looper {
    private Queue<Runnable> queue;
    private boolean shutdown;

    public QueueExecutor() {
        queue = new ShurikenQueue<Runnable>();
        shutdown = false;
    }

    public Queue<Runnable> queue() {
        return queue;
    }

    @Override
    protected long loop() {
        while (queue.hasNext()) {
            try {
                queue.next().run();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        if (shutdown && !queue.hasNext()) {
            interrupt();
            return -1;
        }

        return Math.max(500, (long) getRunTime() * 10);
    }

    public double getRunTime() {
        return 10; //TODO: ACTUALLY CHECK
    }

    public void shutdown() {
        shutdown = true;
    }
}
