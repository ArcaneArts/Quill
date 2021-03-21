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

public enum NoiseType {
    WHITE(seed -> new WhiteNoise(seed)),
    SIMPLEX(seed -> new SimplexNoise(seed)),
    PERLIN(seed -> new PerlinNoise(seed)),
    FRACTAL_BILLOW_SIMPLEX(seed -> new FractalBillowSimplexNoise(seed)),
    FRACTAL_BILLOW_PERLIN(seed -> new FractalBillowPerlinNoise(seed)),
    FRACTAL_FBM_SIMPLEX(seed -> new FractalFBMSimplexNoise(seed)),
    FRACTAL_RIGID_MULTI_SIMPLEX(seed -> new FractalRigidMultiSimplexNoise(seed)),
    FLAT(seed -> new FlatNoise(seed)),
    CELLULAR(seed -> new CellularNoise(seed)),
    GLOB(seed -> new GlobNoise(seed)),
    CUBIC(seed -> new CubicNoise(seed)),
    FRACTAL_CUBIC(seed -> new FractalCubicNoise(seed)),
    CELLULAR_HEIGHT(seed -> new CellHeightNoise(seed)),
    VASCULAR(seed -> new VascularNoise(seed));

    private NoiseFactory f;

    private NoiseType(NoiseFactory f) {
        this.f = f;
    }

    public NoiseGenerator create(long seed) {
        return f.create(seed);
    }
}
