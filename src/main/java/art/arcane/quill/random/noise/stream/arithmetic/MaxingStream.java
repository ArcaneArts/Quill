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

import art.arcane.quill.collections.functional.Function2;
import art.arcane.quill.collections.functional.Function3;
import art.arcane.quill.random.noise.stream.BasicStream;
import art.arcane.quill.random.noise.stream.ProceduralStream;

public class MaxingStream<T> extends BasicStream<T> {
    private final Function3<Double, Double, Double, Double> add;

    public MaxingStream(ProceduralStream<T> stream, Function3<Double, Double, Double, Double> add) {
        super(stream);
        this.add = add;
    }

    public MaxingStream(ProceduralStream<T> stream, Function2<Double, Double, Double> add) {
        this(stream, (x, y, z) -> add.apply(x, z));
    }

    public MaxingStream(ProceduralStream<T> stream, double add) {
        this(stream, (x, y, z) -> add);
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
        return fromDouble(Math.max(add.apply(x, 0D, z), getTypedSource().getDouble(x, z)));
    }

    @Override
    public T get(double x, double y, double z) {
        return fromDouble(Math.max(add.apply(x, y, z), getTypedSource().getDouble(x, y, z)));
    }

}
