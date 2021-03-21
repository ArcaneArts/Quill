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

public class InvertedHunkView<T> implements Hunk<T>
{
	private final Hunk<T> src;

	public InvertedHunkView(Hunk<T> src)
	{
		this.src = src;
	}

	@Override
	public void setRaw(int x, int y, int z, T t)
	{
		src.setRaw(x, (getHeight() -1) - y, z, t);
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
	public int getDepth()
	{
		return src.getDepth();
	}

	@Override
	public int getHeight()
	{
		return src.getHeight();
	}

	@Override
	public Hunk<T> getSource()
	{
		return src;
	}
}
