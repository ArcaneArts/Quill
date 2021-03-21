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

package art.arcane.quill.random;

import art.arcane.quill.collections.KMap;
import art.arcane.quill.collections.Shrinkwrap;

public class WeightMap<T> extends KMap<T, Double> {
    private static final long serialVersionUID = 87558033900969389L;
    private boolean modified = false;
    private double lastWeight = 0;

    public double getPercentChance(T t) {
        if (totalWeight() <= 0) {
            return 0;
        }

        return getWeight(t) / totalWeight();
    }

    public void clear() {
        modified = true;
    }

    public WeightMap<T> setWeight(T t, double weight) {
        modified = true;
        put(t, weight);

        return this;
    }

    public double getWeight(T t) {
        return get(t);
    }

    public double totalWeight() {
        if (!modified) {
            return lastWeight;
        }

        modified = false;
        Shrinkwrap<Double> s = new Shrinkwrap<Double>(0D);
        forEachKey(Integer.MAX_VALUE, (d) -> s.set(s.get() + 1));
        lastWeight = s.get();

        return lastWeight;
    }
}
