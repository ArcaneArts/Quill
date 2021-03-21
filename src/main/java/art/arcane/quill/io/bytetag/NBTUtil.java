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

import java.io.*;
import java.util.zip.GZIPInputStream;

public final class NBTUtil {

    private NBTUtil() {
    }

    public static void write(NBTRawNamedTag tag, File file, boolean compressed) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            new NBTSerializer(compressed).toStream(tag, fos);
        }
    }

    public static void write(NBTRawNamedTag tag, String file, boolean compressed) throws IOException {
        write(tag, new File(file), compressed);
    }

    public static void write(NBTRawNamedTag tag, File file) throws IOException {
        write(tag, file, true);
    }

    public static void write(NBTRawNamedTag tag, String file) throws IOException {
        write(tag, new File(file), true);
    }

    public static void write(NBTTag<?> tag, File file, boolean compressed) throws IOException {
        write(new NBTRawNamedTag(null, tag), file, compressed);
    }

    public static void write(NBTTag<?> tag, String file, boolean compressed) throws IOException {
        write(new NBTRawNamedTag(null, tag), new File(file), compressed);
    }

    public static void write(NBTTag<?> tag, File file) throws IOException {
        write(new NBTRawNamedTag(null, tag), file, true);
    }

    public static void write(NBTTag<?> tag, String file) throws IOException {
        write(new NBTRawNamedTag(null, tag), new File(file), true);
    }

    public static NBTRawNamedTag read(File file, boolean compressed) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            return new NBTDeserializer(compressed).fromStream(fis);
        }
    }

    public static NBTRawNamedTag read(String file, boolean compressed) throws IOException {
        return read(new File(file), compressed);
    }

    public static NBTRawNamedTag read(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            return new NBTDeserializer(false).fromStream(detectDecompression(fis));
        }
    }

    public static NBTRawNamedTag read(String file) throws IOException {
        return read(new File(file));
    }

    private static InputStream detectDecompression(InputStream is) throws IOException {
        PushbackInputStream pbis = new PushbackInputStream(is, 2);
        int signature = (pbis.read() & 0xFF) + (pbis.read() << 8);
        pbis.unread(signature >> 8);
        pbis.unread(signature & 0xFF);
        if (signature == GZIPInputStream.GZIP_MAGIC) {
            return new GZIPInputStream(pbis);
        }
        return pbis;
    }
}
