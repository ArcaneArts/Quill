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

package art.arcane.quill.math;

/**
 * Timer
 *
 * @author cyberpwn
 */
public class Timer {
    private long tns;
    private long cns;

    /**
     * Create a new timer
     */
    public Timer() {
        tns = 0;
        cns = 0;
    }

    /**
     * Start the timer
     */
    public void start() {
        cns = M.ns();
    }

    /**
     * Stop the timer
     */
    public void stop() {
        tns = M.ns() - cns;
        cns = M.ns();
    }

    /**
     * Get time in nanoseconds
     *
     * @return the time
     */
    public long getTime() {
        return tns;
    }

    /**
     * Get the time in nanoseconds the timer last stopped
     *
     * @return
     */
    public long getLastRun() {
        return cns;
    }
}