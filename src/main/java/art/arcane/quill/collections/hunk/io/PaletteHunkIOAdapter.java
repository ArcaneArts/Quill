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

import art.arcane.quill.collections.DataPalette;
import art.arcane.quill.collections.functional.Function3;
import art.arcane.quill.collections.hunk.Hunk;

import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class PaletteHunkIOAdapter<T> implements HunkIOAdapter<T> {
    @Override
    public void write(Hunk<T> t, OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeShort(t.getWidth() + Short.MIN_VALUE);
        dos.writeShort(t.getHeight() + Short.MIN_VALUE);
        dos.writeShort(t.getDepth() + Short.MIN_VALUE);
        AtomicInteger nonNull = new AtomicInteger(0);
        DataPalette<T> palette = new DataPalette<T>();

        t.iterateSync((x,y,z,w) -> {
            if(w != null)
            {
                palette.getIndex(w);
                nonNull.getAndAdd(1);
            }
        });

        palette.write(this, dos);
        dos.writeInt(nonNull.get() + Integer.MIN_VALUE);
        AtomicBoolean failure = new AtomicBoolean(false);
        t.iterateSync((x,y,z,w) -> {
            if(w != null)
            {
                try
                {
                    dos.writeShort(x + Short.MIN_VALUE);
                    dos.writeShort(y + Short.MIN_VALUE);
                    dos.writeShort(z + Short.MIN_VALUE);
                    dos.writeShort(palette.getIndex(w) + Short.MIN_VALUE);
                }

                catch(Throwable e)
                {
                    e.printStackTrace();
                    failure.set(true);
                }
            }
        });

        dos.close();
    }

    @Override
    public Hunk<T> read(Function3<Integer,Integer,Integer,Hunk<T>> factory, InputStream in) throws IOException {
        DataInputStream din = new DataInputStream(in);
        int w = din.readShort() - Short.MIN_VALUE;
        int h = din.readShort() - Short.MIN_VALUE;
        int d = din.readShort() - Short.MIN_VALUE;
        DataPalette<T> palette = DataPalette.getPalette(this, din);
        int e = din.readInt() - Integer.MIN_VALUE;
        Hunk<T> t = factory.apply(w, h, d);

        for(int i = 0; i < e; i++)
        {
            int x = din.readShort() - Short.MIN_VALUE;
            int y = din.readShort() - Short.MIN_VALUE;
            int z = din.readShort() - Short.MIN_VALUE;
            int vf = din.readShort() - Short.MIN_VALUE;

            T v = null;
            if( palette.getPalette().hasIndex(vf))
            {
                v = palette.getPalette().get(vf);
            }

            if(v != null)
            {
                t.setRaw(x,y,z, v);
            }
        }

        in.close();
        return t;
    }
}
