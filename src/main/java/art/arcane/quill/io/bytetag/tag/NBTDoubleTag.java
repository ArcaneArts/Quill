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

public class NBTDoubleTag extends NBTNumberTag<Double> implements Comparable<NBTDoubleTag> {

    public static final byte ID = 6;
    public static final double ZERO_VALUE = 0.0D;

    public NBTDoubleTag() {
        super(ZERO_VALUE);
    }

    public NBTDoubleTag(double value) {
        super(value);
    }

    @Override
    public byte getID() {
        return ID;
    }

    public void setValue(double value) {
        super.setValue(value);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && getValue().equals(((NBTDoubleTag) other).getValue());
    }

    @Override
    public int compareTo(NBTDoubleTag other) {
        return getValue().compareTo(other.getValue());
    }

    @Override
    public NBTDoubleTag clone() {
        return new NBTDoubleTag(getValue());
    }
}
