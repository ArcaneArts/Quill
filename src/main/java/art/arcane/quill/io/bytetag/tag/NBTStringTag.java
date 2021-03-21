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

public class NBTStringTag extends NBTTag<String> implements Comparable<NBTStringTag> {

    public static final byte ID = 8;
    public static final String ZERO_VALUE = "";

    public NBTStringTag() {
        super(ZERO_VALUE);
    }

    public NBTStringTag(String value) {
        super(value);
    }

    @Override
    public byte getID() {
        return ID;
    }

    @Override
    public String getValue() {
        return super.getValue();
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
    }

    @Override
    public String valueToString(int maxDepth) {
        return escapeString(getValue(), false);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && getValue().equals(((NBTStringTag) other).getValue());
    }

    @Override
    public int compareTo(NBTStringTag o) {
        return getValue().compareTo(o.getValue());
    }

    @Override
    public NBTStringTag clone() {
        return new NBTStringTag(getValue());
    }
}
