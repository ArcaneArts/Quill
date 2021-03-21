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

public class CNum {
    private int number;
    private int max;

    public CNum(int max) {
        number = 0;
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public CNum set(int n) {
        number = n;
        circ();
        return this;
    }

    public CNum add(int a) {
        number += a;
        circ();
        return this;
    }

    public CNum sub(int a) {
        number -= a;
        circ();
        return this;
    }

    public int get() {
        return number;
    }

    public void circ() {
        if (number < 0) {
            number = max - (Math.abs(number) > max ? max : Math.abs(number));
        }

        number = number % (max);
    }
}