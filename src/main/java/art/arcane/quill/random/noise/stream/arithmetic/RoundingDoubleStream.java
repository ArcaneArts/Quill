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

public class RoundingDoubleStream extends BasicStream<Double> {
    private final ProceduralStream<?> stream;

    public RoundingDoubleStream(ProceduralStream<?> stream) {
        super();
        this.stream = stream;
    }

    @Override
    public double toDouble(Double t) {
        return t;
    }

    @Override
    public Double fromDouble(double d) {
        return (double) Math.round(d);
    }

    private double round(double v) {
        return Math.round(v);
    }

    @Override
    public Double get(double x, double z) {
        return round(stream.getDouble(x, z));
    }

    @Override
    public Double get(double x, double y, double z) {
        return round(stream.getDouble(x, y, z));
    }

}
