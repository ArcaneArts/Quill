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

package art.arcane.quill.io;

import art.arcane.quill.random.RNG;

import java.io.IOException;
import java.io.InputStream;

public class RandomInputStream extends InputStream {
    private RNG rng;

    public RandomInputStream(String seed) {
        rng = new RNG(seed);
    }

    public RandomInputStream(long seed) {
        rng = new RNG(seed);
    }

    public RandomInputStream() {
        rng = new RNG();
    }

    @Override
    public int read() throws IOException {
        return (int) (rng.imax() % 256);
    }
}
