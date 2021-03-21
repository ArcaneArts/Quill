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

package art.arcane.quill.collections.hunk.view;


import art.arcane.quill.collections.functional.Consumer4;
import art.arcane.quill.collections.hunk.Hunk;

public class ListeningHunk<T> implements Hunk<T> {
    private final Hunk<T> src;
    private final Consumer4<Integer, Integer, Integer, T> listener;

    public ListeningHunk(Hunk<T> src, Consumer4<Integer, Integer, Integer, T> listener)
    {
        this.src = src;
        this.listener = listener;
    }

    @Override
    public void setRaw(int x, int y, int z, T t)
    {
        listener.accept(x,y,z,t);
        src.setRaw(x,y,z,t);
    }

    @Override
    public T getRaw(int x, int y, int z)
    {
        return src.getRaw(x, y, z);
    }

    @Override
    public int getWidth()
    {
        return src.getWidth();
    }

    @Override
    public int getHeight()
    {
        return src.getHeight();
    }

    @Override
    public int getDepth()
    {
        return src.getDepth();
    }

    @Override
    public Hunk<T> getSource()
    {
        return src;
    }
}
