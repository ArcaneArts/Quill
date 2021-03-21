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
import java.net.URL;

public interface NBTRawDeserializer<T> {

    T fromStream(InputStream stream) throws IOException;

    default T fromFile(File file) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            return fromStream(bis);
        }
    }

    default T fromBytes(byte[] data) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        return fromStream(stream);
    }

    default T fromResource(Class<?> clazz, String path) throws IOException {
        try (InputStream stream = clazz.getClassLoader().getResourceAsStream(path)) {
            if (stream == null) {
                throw new IOException("resource \"" + path + "\" not found");
            }
            return fromStream(stream);
        }
    }

    default T fromURL(URL url) throws IOException {
        try (InputStream stream = url.openStream()) {
            return fromStream(stream);
        }
    }


}
