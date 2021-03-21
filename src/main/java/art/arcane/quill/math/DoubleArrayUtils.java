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


public class DoubleArrayUtils {
    public static void shiftRight(double[] values, double push) {
        for (int index = values.length - 2; index >= 0; index--) {
            values[index + 1] = values[index];
        }

        values[0] = push;
    }

    public static void wrapRight(double[] values) {
        double last = values[values.length - 1];
        shiftRight(values, last);
    }

    public static void fill(double[] values, double value) {
        for (int i = 0; i < values.length; i++) {
            values[i] = value;
        }
    }
}
