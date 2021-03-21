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

public class NBTLongArrayTag extends NBTArrayTag<long[]> implements Comparable<NBTLongArrayTag> {

    public static final byte ID = 12;
    public static final long[] ZERO_VALUE = new long[0];

    public NBTLongArrayTag() {
        super(ZERO_VALUE);
    }

    public NBTLongArrayTag(long[] value) {
        super(value);
    }

    @Override
    public byte getID() {
        return ID;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && Arrays.equals(getValue(), ((NBTLongArrayTag) other).getValue());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getValue());
    }

    @Override
    public int compareTo(NBTLongArrayTag other) {
        return Integer.compare(length(), other.length());
    }

    @Override
    public NBTLongArrayTag clone() {
        return new NBTLongArrayTag(Arrays.copyOf(getValue(), length()));
    }
}
