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


import art.arcane.quill.collections.hunk.Hunk;

public class ReadOnlyHunk<T> implements Hunk<T> {
    private final Hunk<T> src;

    public ReadOnlyHunk(Hunk<T> src)
    {
        this.src = src;
    }

    @Override
    public void setRaw(int x, int y, int z, T t)
    {
        throw new IllegalStateException("This hunk is read only!");
    }

    @Override
    public T getRaw(int x, int y, int z)
    {
        return src.getRaw(x, y, z);
    }

    @Override
    public void set(int x1, int y1, int z1, int x2, int y2, int z2, T t)
    {
        throw new IllegalStateException("This hunk is read only!");
    }

    @Override
    public void fill(T t)
    {
        throw new IllegalStateException("This hunk is read only!");
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
