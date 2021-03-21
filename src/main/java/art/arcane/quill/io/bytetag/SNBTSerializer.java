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
import java.io.Writer;

public class SNBTSerializer implements NBTRawStringSerializer<NBTTag<?>> {

    @Override
    public void toWriter(NBTTag<?> tag, Writer writer) throws IOException {
        SNBTWriter.write(tag, writer);
    }

    public void toWriter(NBTTag<?> tag, Writer writer, int maxDepth) throws IOException {
        SNBTWriter.write(tag, writer, maxDepth);
    }
}
