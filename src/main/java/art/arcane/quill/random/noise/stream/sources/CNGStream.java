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


import art.arcane.quill.random.noise.CNG;
import art.arcane.quill.random.noise.stream.BasicLayer;
import art.arcane.quill.random.noise.stream.ProceduralStream;

public class CNGStream extends BasicLayer implements ProceduralStream<Double> {
    private final CNG cng;

    public CNGStream(CNG cng) {
        this.cng = cng;
    }

    public CNGStream(CNG cng, double zoom, double offsetX, double offsetY, double offsetZ) {
        super(1337, zoom, offsetX, offsetY, offsetZ);
        this.cng = cng;
    }

    public CNGStream(CNG cng, double zoom) {
        super(1337, zoom);
        this.cng = cng;
    }

    @Override
    public double toDouble(Double t) {
        return t;
    }

    @Override
    public Double fromDouble(double d) {
        return d;
    }

    @Override
    public ProceduralStream<Double> getTypedSource() {
        return null;
    }

    @Override
    public ProceduralStream<?> getSource() {
        return null;
    }

    @Override
    public Double get(double x, double z) {
        return cng.noise((x + getOffsetX()) / getZoom(), (z + getOffsetZ()) / getZoom());
    }

    @Override
    public Double get(double x, double y, double z) {
        return cng.noise((x + getOffsetX()) / getZoom(), (y + getOffsetY()) / getZoom(), (z + getOffsetZ()) * getZoom());
    }

}
