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

public class ClampedStream<T> extends BasicStream<T> implements ProceduralStream<T> {
    private final double min;
    private final double max;

    public ClampedStream(ProceduralStream<T> stream, double min, double max) {
        super(stream);
        this.min = min;
        this.max = max;
    }

    @Override
    public double toDouble(T t) {
        return getTypedSource().toDouble(t);
    }

    @Override
    public T fromDouble(double d) {
        return getTypedSource().fromDouble(d);
    }

    private double clamp(double v) {
        return Math.max(Math.min(v, max), min);
    }

    @Override
    public T get(double x, double z) {
        return fromDouble(clamp(getTypedSource().getDouble(x, z)));
    }

    @Override
    public T get(double x, double y, double z) {
        return fromDouble(clamp(getTypedSource().getDouble(x, y, z)));
    }

}
