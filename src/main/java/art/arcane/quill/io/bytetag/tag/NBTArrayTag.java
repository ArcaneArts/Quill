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

import java.lang.reflect.Array;

/**
 * ArrayTag is an abstract representation of any NBT array tag.
 * For implementations see {@link NBTByteArrayTag}, {@link NBTIntArrayTag}, {@link NBTLongArrayTag}.
 *
 * @param <T> The array type.
 */
public abstract class NBTArrayTag<T> extends NBTTag<T> {

    public NBTArrayTag(T value) {
        super(value);
        if (!value.getClass().isArray()) {
            throw new UnsupportedOperationException("type of array tag must be an array");
        }
    }

    public int length() {
        return Array.getLength(getValue());
    }

    @Override
    public T getValue() {
        return super.getValue();
    }

    @Override
    public void setValue(T value) {
        super.setValue(value);
    }

    @Override
    public String valueToString(int maxDepth) {
        return arrayToString("", "");
    }

    protected String arrayToString(String prefix, String suffix) {
        StringBuilder sb = new StringBuilder("[").append(prefix).append("".equals(prefix) ? "" : ";");
        for (int i = 0; i < length(); i++) {
            sb.append(i == 0 ? "" : ",").append(Array.get(getValue(), i)).append(suffix);
        }
        sb.append("]");
        return sb.toString();
    }
}
