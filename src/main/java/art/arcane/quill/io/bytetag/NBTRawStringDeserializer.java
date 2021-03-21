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

import java.io.*;

public interface NBTRawStringDeserializer<T> extends NBTRawDeserializer<T> {

    T fromReader(Reader reader) throws IOException;

    default T fromString(String s) throws IOException {
        return fromReader(new StringReader(s));
    }

    @Override
    default T fromStream(InputStream stream) throws IOException {
        try (Reader reader = new InputStreamReader(stream)) {
            return fromReader(reader);
        }
    }

    @Override
    default T fromFile(File file) throws IOException {
        try (Reader reader = new FileReader(file)) {
            return fromReader(reader);
        }
    }

    @Override
    default T fromBytes(byte[] data) throws IOException {
        return fromReader(new StringReader(new String(data)));
    }
}
