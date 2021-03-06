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

package art.arcane.quill.random.noise.stream.interpolation;

import art.arcane.quill.random.noise.stream.ProceduralStream;

import java.util.function.Function;

public interface Interpolated<T> {
    public static final Interpolated<art.arcane.quill.random.RNG> RNG = of((t) -> 0D, (t) -> null);
    public static final Interpolated<Double> DOUBLE = of((t) -> t, (t) -> t);
    public static final Interpolated<Integer> INT = of((t) -> Double.valueOf(t), (t) -> t.intValue());
    public static final Interpolated<Long> LONG = of((t) -> Double.valueOf(t), (t) -> t.longValue());

    public static <T> Interpolated<T> of(Function<T, Double> a, Function<Double, T> b) {
        return new Interpolated<T>() {
            @Override
            public double toDouble(T t) {
                return a.apply(t);
            }

            @Override
            public T fromDouble(double d) {
                return b.apply(d);
            }
        };
    }

    public double toDouble(T t);

    public T fromDouble(double d);

    default InterpolatorFactory<T> interpolate() {
        if (this instanceof ProceduralStream) {
            return new InterpolatorFactory<T>((ProceduralStream<T>) this);
        }

        return null;
    }
}
