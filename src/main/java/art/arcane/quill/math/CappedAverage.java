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

import java.util.Collections;


public class CappedAverage extends Average {
    protected int trim;

    public CappedAverage(int size, int trim) {
        super(size);

        if (trim * 2 >= size) {
            throw new RuntimeException("Trim cannot be >= half the average size");
        }

        this.trim = trim;
    }

    protected double computeAverage() {
        double a = 0;

        KList<Double> minmax = new KList<Double>();
        for (double i : values) {
            minmax.add(i);
        }
        Collections.sort(minmax);

        for (int i = trim; i < values.length - trim; i++) {
            a += minmax.get(i);
        }

        return a / ((double) values.length - (double) trim);
    }
}
