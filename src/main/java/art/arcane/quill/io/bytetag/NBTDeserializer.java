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
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class NBTDeserializer implements NBTRawDeserializer<NBTRawNamedTag> {

    private boolean compressed;

    public NBTDeserializer() {
        this(true);
    }

    public NBTDeserializer(boolean compressed) {
        this.compressed = compressed;
    }

    @Override
    public NBTRawNamedTag fromStream(InputStream stream) throws IOException {
        NBTInputStream nbtIn;
        if (compressed) {
            nbtIn = new NBTInputStream(new GZIPInputStream(stream));
        } else {
            nbtIn = new NBTInputStream(stream);
        }
        return nbtIn.readTag(NBTTag.DEFAULT_MAX_DEPTH);
    }
}
