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


import art.arcane.quill.math.IrisInterpolation;
import art.arcane.quill.random.noise.stream.BasicStream;
import art.arcane.quill.random.noise.stream.ProceduralStream;

public class BilinearStream<T> extends BasicStream<T> implements Interpolator<T> {
    private final int rx;
    private final int ry;

    public BilinearStream(ProceduralStream<T> stream, int rx, int ry) {
        super(stream);
        this.rx = rx;
        this.ry = ry;
    }

    public T interpolate(double x, double y) {
        int fx = (int) Math.floor(x / rx);
        int fz = (int) Math.floor(y / ry);
        int x1 = (int) Math.round(fx * rx);
        int z1 = (int) Math.round(fz * ry);
        int x2 = (int) Math.round((fx + 1) * rx);
        int z2 = (int) Math.round((fz + 1) * ry);
        double px = IrisInterpolation.rangeScale(0, 1, x1, x2, x);
        double pz = IrisInterpolation.rangeScale(0, 1, z1, z2, y);

        //@builder
        return getTypedSource().fromDouble(IrisInterpolation.blerp(
                getTypedSource().getDouble(x1, z1),
                getTypedSource().getDouble(x2, z1),
                getTypedSource().getDouble(x1, z2),
                getTypedSource().getDouble(x2, z2),
                px, pz));
        //@done
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
        return interpolate(x, z);
    }

    @Override
    public T get(double x, double y, double z) {
        return interpolate(x, z);
    }
}
