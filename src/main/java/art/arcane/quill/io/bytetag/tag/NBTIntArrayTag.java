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

package art.arcane.quill.io.bytetag.tag;

import java.util.Arrays;

public class NBTIntArrayTag extends NBTArrayTag<int[]> implements Comparable<NBTIntArrayTag> {

    public static final byte ID = 11;
    public static final int[] ZERO_VALUE = new int[0];

    public NBTIntArrayTag() {
        super(ZERO_VALUE);
    }

    public NBTIntArrayTag(int[] value) {
        super(value);
    }

    @Override
    public byte getID() {
        return ID;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && Arrays.equals(getValue(), ((NBTIntArrayTag) other).getValue());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getValue());
    }

    @Override
    public int compareTo(NBTIntArrayTag other) {
        return Integer.compare(length(), other.length());
    }

    @Override
    public NBTIntArrayTag clone() {
        return new NBTIntArrayTag(Arrays.copyOf(getValue(), length()));
    }
}
