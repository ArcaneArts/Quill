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

package art.arcane.quill.collections.hunk.io;

import art.arcane.quill.collections.functional.Function3;
import art.arcane.quill.collections.hunk.Hunk;
import art.arcane.quill.io.CustomOutputStream;
import art.arcane.quill.io.IOAdapter;
import art.arcane.quill.io.bytetag.jnbt.ByteArrayTag;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public interface HunkIOAdapter<T> extends IOAdapter<T>
{
    public void write(Hunk<T> t, OutputStream out) throws IOException;

    public Hunk<T> read(Function3<Integer,Integer,Integer,Hunk<T>> factory, InputStream in) throws IOException;

    default void write(Hunk<T> t, File f) throws IOException
    {
        f.getParentFile().mkdirs();
        FileOutputStream fos = new FileOutputStream(f);
        GZIPOutputStream gzo = new CustomOutputStream(fos, 6);
        write(t, gzo);
    }

    default Hunk<T> read(Function3<Integer,Integer,Integer,Hunk<T>> factory, File f) throws IOException
    {
        return read(factory, new GZIPInputStream(new FileInputStream(f)));
    }

    default Hunk<T> read(Function3<Integer,Integer,Integer,Hunk<T>> factory, ByteArrayTag f) throws IOException
    {
        return read(factory, new ByteArrayInputStream(f.getValue()));
    }

    default ByteArrayTag writeByteArrayTag(Hunk<T> tHunk, String name) throws IOException
    {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        write(tHunk, boas);
        return new ByteArrayTag(name, boas.toByteArray());
    }
}
