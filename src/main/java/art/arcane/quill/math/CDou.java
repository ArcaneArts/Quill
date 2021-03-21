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

public class CDou {
    private double number;
    private double max;

    public CDou(double max) {
        number = 0;
        this.max = max;
    }

    public CDou set(double n) {
        number = n;
        circ();
        return this;
    }

    public CDou add(double a) {
        number += a;
        circ();
        return this;
    }

    public CDou sub(double a) {
        number -= a;
        circ();
        return this;
    }

    public double get() {
        return number;
    }

    public void circ() {
        if (number < 0) {
            number = max - (Math.abs(number) > max ? max : Math.abs(number));
        }

        number = number % (max);
    }
}