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

package art.arcane.quill.random.noise.stream;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasicLayer implements ProceduralLayer {
    private final long seed;
    private final double zoom;
    private final double offsetX;
    private final double offsetY;
    private final double offsetZ;

    public BasicLayer(long seed, double zoom) {
        this(seed, zoom, 0D, 0D, 0D);
    }

    public BasicLayer(long seed) {
        this(seed, 1D);
    }

    public BasicLayer() {
        this(1337);
    }
}
