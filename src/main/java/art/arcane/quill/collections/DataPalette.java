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

package art.arcane.quill.collections;

import art.arcane.quill.io.IOAdapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DataPalette<T> {
    private final KList<T> palette;

    public DataPalette()
    {
        this(new KList<T>(16));
    }

    public DataPalette(KList<T> palette)
    {
        this.palette = palette;
    }

    public KList<T> getPalette()
    {
        return palette;
    }

    public int getIndex(T t)
    {
        int v = 0;

       synchronized (palette)
       {
           v = palette.indexOf(t);

           if(v == -1)
           {
               v = palette.size();
               palette.add(t);
           }
       }

        return v;
    }

    public void write(IOAdapter<T> adapter, DataOutputStream dos) throws IOException
    {
        synchronized (palette)
        {
            dos.writeShort(getPalette().size() + Short.MIN_VALUE);

            for(int i = 0; i < palette.size(); i++)
            {
                adapter.write(palette.get(i), dos);
            }
        }
    }

    public static <T> DataPalette<T> getPalette(IOAdapter<T> adapter, DataInputStream din) throws IOException
    {
        KList<T> palette = new KList<>();
        int s = din.readShort() - Short.MIN_VALUE;

        for(int i = 0; i < s; i++)
        {
            palette.add(adapter.read(din));
        }

        return new DataPalette<>(palette);
    }
}
