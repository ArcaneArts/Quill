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

package art.arcane.quill.random.noise.stream.sources;


import art.arcane.quill.collections.functional.Function2;
import art.arcane.quill.collections.functional.Function3;
import art.arcane.quill.random.noise.stream.BasicStream;
import art.arcane.quill.random.noise.stream.interpolation.Interpolated;

public class FunctionStream<T> extends BasicStream<T> {
    private Function2<Double, Double, T> f2;
    private Function3<Double, Double, Double, T> f3;
    private Interpolated<T> helper;

    public FunctionStream(Function2<Double, Double, T> f2, Function3<Double, Double, Double, T> f3, Interpolated<T> helper) {
        super();
        this.f2 = f2;
        this.f3 = f3;
        this.helper = helper;
    }

    @Override
    public double toDouble(T t) {
        return helper.toDouble(t);
    }

    @Override
    public T fromDouble(double d) {
        return helper.fromDouble(d);
    }

    @Override
    public T get(double x, double z) {
        return f2.apply(x, z);
    }

    @Override
    public T get(double x, double y, double z) {
        return f3.apply(x, y, z);
    }
}
