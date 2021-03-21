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

public class FittedStream<T> extends BasicStream<T> implements ProceduralStream<T> {
    private final double min;
    private final double max;
    private final double inMin;
    private final double inMax;

    public FittedStream(ProceduralStream<T> stream, double inMin, double inMax, double min, double max) {
        super(stream);
        this.inMin = inMin;
        this.inMax = inMax;
        this.min = min;
        this.max = max;
    }

    public FittedStream(ProceduralStream<T> stream, double min, double max) {
        this(stream, 0, 1, min, max);
    }

    @Override
    public double toDouble(T t) {
        return getTypedSource().toDouble(t);
    }

    @Override
    public T fromDouble(double d) {
        return getTypedSource().fromDouble(d);
    }

    private double dlerp(double v) {
        return min + ((max - min) * ((v - inMin) / (inMax - inMin)));
    }

    @Override
    public T get(double x, double z) {
        return fromDouble(dlerp(getTypedSource().getDouble(x, z)));
    }

    @Override
    public T get(double x, double y, double z) {
        return fromDouble(dlerp(getTypedSource().getDouble(x, y, z)));
    }

}
