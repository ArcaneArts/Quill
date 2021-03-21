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


import art.arcane.quill.random.noise.stream.BasicStream;
import art.arcane.quill.random.noise.stream.ProceduralStream;

public class BiStarcastStream<T> extends BasicStream<T> implements Interpolator<T> {
    private int rad;
    private int checks;

    public BiStarcastStream(ProceduralStream<T> stream, int rad, int checks) {
        super(stream);
        this.rad = rad;
        this.checks = checks;
    }

    public T interpolate(double x, double y) {
        double m = (360D / checks);
        double v = 0;

        for (int i = 0; i < 360; i += m) {
            double sin = Math.sin(Math.toRadians(i));
            double cos = Math.cos(Math.toRadians(i));
            double cx = x + ((rad * cos) - (rad * sin));
            double cz = y + ((rad * sin) + (rad * cos));
            v += getTypedSource().getDouble(cx, cz);
        }

        return getTypedSource().fromDouble(v / checks);
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
