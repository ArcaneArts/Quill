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

public class NBTShortTag extends NBTNumberTag<Short> implements Comparable<NBTShortTag> {

    public static final byte ID = 2;
    public static final short ZERO_VALUE = 0;

    public NBTShortTag() {
        super(ZERO_VALUE);
    }

    public NBTShortTag(short value) {
        super(value);
    }

    @Override
    public byte getID() {
        return ID;
    }

    public void setValue(short value) {
        super.setValue(value);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && asShort() == ((NBTShortTag) other).asShort();
    }

    @Override
    public int compareTo(NBTShortTag other) {
        return getValue().compareTo(other.getValue());
    }

    @Override
    public NBTShortTag clone() {
        return new NBTShortTag(getValue());
    }
}
