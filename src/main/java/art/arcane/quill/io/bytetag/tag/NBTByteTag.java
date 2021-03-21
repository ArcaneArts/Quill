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

public class NBTByteTag extends NBTNumberTag<Byte> implements Comparable<NBTByteTag> {

    public static final byte ID = 1;
    public static final byte ZERO_VALUE = 0;

    public NBTByteTag() {
        super(ZERO_VALUE);
    }

    public NBTByteTag(byte value) {
        super(value);
    }

    public NBTByteTag(boolean value) {
        super((byte) (value ? 1 : 0));
    }

    @Override
    public byte getID() {
        return ID;
    }

    public boolean asBoolean() {
        return getValue() > 0;
    }

    public void setValue(byte value) {
        super.setValue(value);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && asByte() == ((NBTByteTag) other).asByte();
    }

    @Override
    public int compareTo(NBTByteTag other) {
        return getValue().compareTo(other.getValue());
    }

    @Override
    public NBTByteTag clone() {
        return new NBTByteTag(getValue());
    }
}
