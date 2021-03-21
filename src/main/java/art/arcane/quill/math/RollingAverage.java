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

import java.util.Arrays;

/**
 * Represents a rolling average
 *
 * @author cyberpwn
 */
public class RollingAverage {
    protected Double[] data;
    protected double average;
    protected boolean dirty;
    protected int currentIndex;

    /**
     * Create a rolling average
     *
     * @param size the size
     */
    public RollingAverage(int size) {
        data = new Double[size];
        put(0D);
    }

    /**
     * Put data in the average
     *
     * @param d the double
     */
    public void put(double d) {
        currentIndex = currentIndex >= data.length ? 0 : currentIndex;
        data[currentIndex++] = d;
        dirty = true;
    }

    /**
     * Get the cached average
     *
     * @return the average
     */
    public double get() {
        if (dirty) {
            average = computeAverage();
            dirty = false;
        }

        return average;
    }

    protected double computeAverage() {
        try {
            double a = 0;

            for (int i = 0; i < data.length; i++) {
                a += data[i];
            }

            return a / data.length;
        } catch (Throwable e) {

        }

        return 0;
    }

    /**
     * Clear the average data
     *
     * @param v the value to clear with (i.e. 0)
     */
    public void clear(double v) {
        Arrays.fill(data, v);
        average = v;
        dirty = false;
    }

    public int size() {
        return data.length;
    }
}
