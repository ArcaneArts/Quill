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

package art.arcane.quill.random.noise.stream.utility;

import art.arcane.quill.random.noise.stream.BasicStream;
import art.arcane.quill.random.noise.stream.ProceduralStream;

public class NullSafeStream<T> extends BasicStream<T> implements ProceduralStream<T> {
    private final ProceduralStream<T> stream;
    private final T ifNull;

    public NullSafeStream(ProceduralStream<T> stream, T ifNull) {
        super();
        this.stream = stream;
        this.ifNull = ifNull;
    }

    @Override
    public double toDouble(T t) {
        return stream.toDouble(t);
    }

    @Override
    public T fromDouble(double d) {
        return stream.fromDouble(d);
    }

    @Override
    public T get(double x, double z) {
        T t = stream.get(x, z);

        if (t == null) {
            return ifNull;
        }

        return t;
    }

    @Override
    public T get(double x, double y, double z) {
        T t = stream.get(x, y, z);

        if (t == null) {
            return ifNull;
        }

        return t;
    }
}
