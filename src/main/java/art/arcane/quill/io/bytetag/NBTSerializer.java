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

package art.arcane.quill.io.bytetag;

import art.arcane.quill.io.bytetag.tag.NBTTag;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class NBTSerializer implements NBTRawSerializer<NBTRawNamedTag> {

    private boolean compressed;

    public NBTSerializer() {
        this(true);
    }

    public NBTSerializer(boolean compressed) {
        this.compressed = compressed;
    }

    @Override
    public void toStream(NBTRawNamedTag object, OutputStream out) throws IOException {
        NBTOutputStream nbtOut;
        if (compressed) {
            nbtOut = new NBTOutputStream(new GZIPOutputStream(out, true));
        } else {
            nbtOut = new NBTOutputStream(out);
        }
        nbtOut.writeTag(object, NBTTag.DEFAULT_MAX_DEPTH);
        nbtOut.flush();
    }
}
