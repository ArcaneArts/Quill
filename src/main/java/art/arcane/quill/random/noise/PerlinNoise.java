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

public class PerlinNoise implements NoiseGenerator, OctaveNoise {
    private final FastNoiseDouble n;
    private int octaves;

    public PerlinNoise(long seed) {
        this.n = new FastNoiseDouble(new RNG(seed).lmax());
        octaves = 1;
    }

    public double f(double v) {
        return (v / 2D) + 0.5D;
    }

    @Override
    public double noise(double x) {
        if (octaves <= 1) {
            return f(n.GetPerlin(x, 0));
        }

        double f = 1;
        double m = 0;
        double v = 0;

        for (int i = 0; i < octaves; i++) {
            v += n.GetPerlin((x * (f == 1 ? f++ : (f *= 2))), 0) * f;
            m += f;
        }

        return f(v / m);
    }

    @Override
    public double noise(double x, double z) {
        if (octaves <= 1) {
            return f(n.GetPerlin(x, z));
        }
        double f = 1;
        double m = 0;
        double v = 0;

        for (int i = 0; i < octaves; i++) {
            f = f == 1 ? f + 1 : f * 2;
            v += n.GetPerlin((x * f), (z * f)) * f;
            m += f;
        }

        return f(v / m);
    }

    @Override
    public double noise(double x, double y, double z) {
        if (octaves <= 1) {
            return f(n.GetPerlin(x, y, z));
        }
        double f = 1;
        double m = 0;
        double v = 0;

        for (int i = 0; i < octaves; i++) {
            f = f == 1 ? f + 1 : f * 2;
            v += n.GetPerlin((x * f), (y * f), (z * f)) * f;
            m += f;
        }

        return f(v / m);
    }

    @Override
    public void setOctaves(int o) {
        octaves = o;
    }
}
