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

import art.arcane.quill.math.M;
import art.arcane.quill.random.RNG;

public class CellHeightNoise implements NoiseGenerator {
    private final FastNoiseDouble n;

    public CellHeightNoise(long seed) {
        this.n = new FastNoiseDouble(new RNG(seed).lmax());
        n.setNoiseType(FastNoiseDouble.NoiseType.Cellular);
        n.setCellularReturnType(FastNoiseDouble.CellularReturnType.Distance2Sub);
        n.setCellularDistanceFunction(FastNoiseDouble.CellularDistanceFunction.Natural);
    }

    private double filter(double noise) {
        return M.clip(1D - ((noise / 2D) + 0.5D), 0D, 1D);
    }

    @Override
    public double noise(double x) {
        return filter(n.GetCellular(x, 0));
    }

    @Override
    public double noise(double x, double z) {
        return filter(n.GetCellular(x, z));
    }

    @Override
    public double noise(double x, double y, double z) {
        return filter(n.GetCellular(x, y, z));
    }
}
