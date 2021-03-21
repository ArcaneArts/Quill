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

public class CellularNoise implements NoiseGenerator {
    private final FastNoiseDouble n;

    public CellularNoise(long seed) {
        this.n = new FastNoiseDouble(new RNG(seed).lmax());
        n.setNoiseType(FastNoiseDouble.NoiseType.Cellular);
        n.setCellularReturnType(FastNoiseDouble.CellularReturnType.CellValue);
        n.setCellularDistanceFunction(FastNoiseDouble.CellularDistanceFunction.Natural);
    }

    @Override
    public double noise(double x) {
        return (n.GetCellular(x, 0) / 2D) + 0.5D;
    }

    @Override
    public double noise(double x, double z) {
        return (n.GetCellular(x, z) / 2D) + 0.5D;
    }

    @Override
    public double noise(double x, double y, double z) {
        return (n.GetCellular(x, y, z) / 2D) + 0.5D;
    }
}
