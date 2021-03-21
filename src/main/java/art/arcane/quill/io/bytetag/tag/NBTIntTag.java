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

public class NBTIntTag extends NBTNumberTag<Integer> implements Comparable<NBTIntTag> {

    public static final byte ID = 3;
    public static final int ZERO_VALUE = 0;

    public NBTIntTag() {
        super(ZERO_VALUE);
    }

    public NBTIntTag(int value) {
        super(value);
    }

    @Override
    public byte getID() {
        return ID;
    }

    public void setValue(int value) {
        super.setValue(value);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && asInt() == ((NBTIntTag) other).asInt();
    }

    @Override
    public int compareTo(NBTIntTag other) {
        return getValue().compareTo(other.getValue());
    }

    @Override
    public NBTIntTag clone() {
        return new NBTIntTag(getValue());
    }
}
