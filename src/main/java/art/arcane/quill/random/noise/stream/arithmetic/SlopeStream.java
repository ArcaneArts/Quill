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

package art.arcane.quill.random.noise.stream.arithmetic;

import art.arcane.quill.random.noise.stream.BasicStream;
import art.arcane.quill.random.noise.stream.ProceduralStream;

public class SlopeStream<T> extends BasicStream<T> {
    private final int range;

    public SlopeStream(ProceduralStream<T> stream, int range) {
        super(stream);
        this.range = range;
    }

    @Override
    public double toDouble(T t) {
        return getTypedSource().toDouble(t);
    }

    @Override
    public T fromDouble(double d) {
        return getTypedSource().fromDouble(d);
    }

    @Override
    public T get(double x, double z) {
        double height = getTypedSource().getDouble(x, z);
        double dx = getTypedSource().getDouble(x + range, z) - height;
        double dy = getTypedSource().getDouble(x, z + range) - height;

        return fromDouble(Math.sqrt(dx * dx + dy * dy));
    }

    @Override
    public T get(double x, double y, double z) {
        double height = getTypedSource().getDouble(x, y, z);
        double dx = getTypedSource().getDouble(x + range, y, z) - height;
        double dy = getTypedSource().getDouble(x, y + range, z) - height;
        double dz = getTypedSource().getDouble(x, y, z + range) - height;

        return fromDouble(Math.cbrt((dx * dx) + (dy * dy) + (dz * dz)));
    }

}
