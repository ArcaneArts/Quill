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

public class Scoop {
    private final byte[] buf;
    private final int size;
    private final boolean done;

    public Scoop(byte[] buf, int size, boolean done) {
        this.buf = buf;
        this.size = size;
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }

    public byte[] getBuf() {
        return buf;
    }

    public int getSize() {
        return size;
    }
}
