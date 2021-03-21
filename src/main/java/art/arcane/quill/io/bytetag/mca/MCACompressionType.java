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

package art.arcane.quill.io.bytetag.mca;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

public enum MCACompressionType {

    NONE(0, t -> t, t -> t),
    GZIP(1, GZIPOutputStream::new, GZIPInputStream::new),
    ZLIB(2, DeflaterOutputStream::new, InflaterInputStream::new);

    private byte id;
    private MCAExceptionFunction<OutputStream, ? extends OutputStream, IOException> compressor;
    private MCAExceptionFunction<InputStream, ? extends InputStream, IOException> decompressor;

    MCACompressionType(int id,
                       MCAExceptionFunction<OutputStream, ? extends OutputStream, IOException> compressor,
                       MCAExceptionFunction<InputStream, ? extends InputStream, IOException> decompressor) {
        this.id = (byte) id;
        this.compressor = compressor;
        this.decompressor = decompressor;
    }

    public static MCACompressionType getFromID(byte id) {
        for (MCACompressionType c : MCACompressionType.values()) {
            if (c.id == id) {
                return c;
            }
        }
        return null;
    }

    public byte getID() {
        return id;
    }

    public OutputStream compress(OutputStream out) throws IOException {
        return compressor.accept(out);
    }

    public InputStream decompress(InputStream in) throws IOException {
        return decompressor.accept(in);
    }
}
