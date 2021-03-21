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

public final class NBTEndTag extends NBTTag<Void> {

    public static final byte ID = 0;
    public static final NBTEndTag INSTANCE = new NBTEndTag();

    private NBTEndTag() {
        super(null);
    }

    @Override
    public byte getID() {
        return ID;
    }

    @Override
    protected Void checkValue(Void value) {
        return value;
    }

    @Override
    public String valueToString(int maxDepth) {
        return "\"end\"";
    }

    @Override
    public NBTEndTag clone() {
        return INSTANCE;
    }
}
