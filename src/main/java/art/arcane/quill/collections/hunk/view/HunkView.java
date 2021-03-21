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

public class HunkView<T> implements Hunk<T>
{
	private final int ox;
	private final int oy;
	private final int oz;
	private final int w;
	private final int h;
	private final int d;
	private final Hunk<T> src;

	public HunkView(Hunk<T> src)
	{
		this(src, src.getWidth(), src.getHeight(), src.getDepth());
	}

	public HunkView(Hunk<T> src, int w, int h, int d)
	{
		this(src, w, h, d, 0, 0, 0);
	}

	public HunkView(Hunk<T> src, int w, int h, int d, int ox, int oy, int oz)
	{
		this.src = src;
		this.w = w;
		this.h = h;
		this.d = d;
		this.ox = ox;
		this.oy = oy;
		this.oz = oz;
	}

	@Override
	public void setRaw(int x, int y, int z, T t)
	{
		src.setRaw(x + ox, y + oy, z + oz, t);
	}

	@Override
	public T getRaw(int x, int y, int z)
	{
		return src.getRaw(x + ox, y + oy, z + oz);
	}

	@Override
	public int getWidth()
	{
		return w;
	}

	@Override
	public int getDepth()
	{
		return d;
	}

	@Override
	public int getHeight()
	{
		return h;
	}

	@Override
	public Hunk<T> getSource()
	{
		return src;
	}
}
