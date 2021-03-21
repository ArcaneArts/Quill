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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.stream.Collectors;

public class SNBTDeserializer implements NBTRawStringDeserializer<NBTTag<?>> {

    @Override
    public NBTTag<?> fromReader(Reader reader) throws IOException {
        return fromReader(reader, NBTTag.DEFAULT_MAX_DEPTH);
    }

    public NBTTag<?> fromReader(Reader reader, int maxDepth) throws IOException {
        BufferedReader bufferedReader;
        if (reader instanceof BufferedReader) {
            bufferedReader = (BufferedReader) reader;
        } else {
            bufferedReader = new BufferedReader(reader);
        }
        return SNBTParser.parse(bufferedReader.lines().collect(Collectors.joining()), maxDepth);
    }
}
