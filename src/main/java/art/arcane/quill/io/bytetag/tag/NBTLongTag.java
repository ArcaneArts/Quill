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

public class NBTLongTag extends NBTNumberTag<Long> implements Comparable<NBTLongTag> {

    public static final byte ID = 4;
    public static final long ZERO_VALUE = 0L;

    public NBTLongTag() {
        super(ZERO_VALUE);
    }

    public NBTLongTag(long value) {
        super(value);
    }

    @Override
    public byte getID() {
        return ID;
    }

    public void setValue(long value) {
        super.setValue(value);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && asLong() == ((NBTLongTag) other).asLong();
    }

    @Override
    public int compareTo(NBTLongTag other) {
        return getValue().compareTo(other.getValue());
    }

    @Override
    public NBTLongTag clone() {
        return new NBTLongTag(getValue());
    }
}
