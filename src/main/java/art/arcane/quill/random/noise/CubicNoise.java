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

package art.arcane.quill.random.noise;

import art.arcane.quill.random.RNG;

public class CubicNoise implements NoiseGenerator {
    private final FastNoiseDouble n;

    public CubicNoise(long seed) {
        this.n = new FastNoiseDouble(new RNG(seed).lmax());
    }

    private double f(double n) {
        return (n / 2D) + 0.5D;
    }

    @Override
    public double noise(double x) {
        return f(n.GetCubic(x, 0));
    }

    @Override
    public double noise(double x, double z) {
        return f(n.GetCubic(x, z));
    }

    @Override
    public double noise(double x, double y, double z) {
        return f(n.GetCubic(x, y, z));
    }
}
