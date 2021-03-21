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

import art.arcane.quill.collections.KList;

public class RollingSequence extends Average {
    private double median;
    private double max;
    private double min;
    private boolean dirtyMedian;
    private int dirtyExtremes;
    private boolean precision;

    public RollingSequence(int size) {
        super(size);
        median = 0;
        min = 0;
        max = 0;
        setPrecision(false);
    }

    public double addLast(int amt) {
        double f = 0;

        for (int i = 0; i < Math.min(values.length, amt); i++) {
            f += values[i];
        }

        return f;
    }

    public boolean isPrecision() {
        return precision;
    }

    public void setPrecision(boolean p) {
        this.precision = p;
    }

    public double getMin() {
        if (dirtyExtremes > (isPrecision() ? 0 : values.length)) {
            resetExtremes();
        }

        return min;
    }

    public double getMax() {
        if (dirtyExtremes > (isPrecision() ? 0 : values.length)) {
            resetExtremes();
        }

        return max;
    }

    public double getMedian() {
        if (dirtyMedian) {
            recalculateMedian();
        }

        return median;
    }

    private void recalculateMedian() {
        median = new KList<Double>().forceAdd(values).sort().middleValue();
        dirtyMedian = false;
    }

    public void resetExtremes() {
        max = Integer.MIN_VALUE;
        min = Integer.MAX_VALUE;

        for (double i : values) {
            max = M.max(max, i);
            min = M.min(min, i);
        }

        dirtyExtremes = 0;
    }

    public void put(double i) {
        super.put(i);
        dirtyMedian = true;
        dirtyExtremes++;
        max = M.max(max, i);
        min = M.min(min, i);
    }
}
