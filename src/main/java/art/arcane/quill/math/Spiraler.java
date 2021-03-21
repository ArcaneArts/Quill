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

package art.arcane.quill.math;

public class Spiraler {
    int x, z, dx, dz, sizeX, sizeZ, t, maxI, i;
    int ox, oz;
    private Spiraled spiraled;

    public Spiraler(int sizeX, int sizeZ, Spiraled spiraled) {
        ox = 0;
        oz = 0;
        this.spiraled = spiraled;
        retarget(sizeX, sizeZ);
    }

    static void Spiral(int X, int Y) {

    }

    public void drain() {
        while (hasNext()) {
            next();
        }
    }

    public Spiraler setOffset(int ox, int oz) {
        this.ox = ox;
        this.oz = oz;
        return this;
    }

    public void retarget(int sizeX, int sizeZ) {
        this.sizeX = sizeX;
        this.sizeZ = sizeZ;
        x = z = dx = 0;
        dz = -1;
        i = 0;
        t = Math.max(sizeX, sizeZ);
        maxI = t * t;
    }

    public boolean hasNext() {
        return i < maxI;
    }

    public void next() {
        if ((-sizeX / 2 <= x) && (x <= sizeX / 2) && (-sizeZ / 2 <= z) && (z <= sizeZ / 2)) {
            spiraled.on(x + ox, z + ox);
        }

        if ((x == z) || ((x < 0) && (x == -z)) || ((x > 0) && (x == 1 - z))) {
            t = dx;
            dx = -dz;
            dz = t;
        }
        x += dx;
        z += dz;
        i++;
    }
}
