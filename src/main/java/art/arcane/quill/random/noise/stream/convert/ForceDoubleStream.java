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

package art.arcane.quill.random.noise.stream.convert;


import art.arcane.quill.random.noise.stream.BasicStream;
import art.arcane.quill.random.noise.stream.ProceduralStream;

public class ForceDoubleStream extends BasicStream<Double> {
    private ProceduralStream<?> stream;

    public ForceDoubleStream(ProceduralStream<?> stream) {
        super(null);
    }

    @Override
    public double toDouble(Double t) {
        return t;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Double fromDouble(double d) {
        return d;
    }

    @Override
    public Double get(double x, double z) {
        return stream.getDouble(x, z);
    }

    @Override
    public Double get(double x, double y, double z) {
        return stream.getDouble(x, y, z);
    }

}
