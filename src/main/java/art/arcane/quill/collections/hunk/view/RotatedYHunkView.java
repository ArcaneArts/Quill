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

public class RotatedYHunkView<T> implements Hunk<T>
{
	private final Hunk<T> src;
	private final double sin;
	private final double cos;

	public RotatedYHunkView(Hunk<T> src, double deg)
	{
		this.src = src;
		this.sin = Math.sin(Math.toRadians(deg));
		this.cos = Math.cos(Math.toRadians(deg));
	}

	@Override
	public void setRaw(int x, int y, int z, T t)
	{
		int xc = (int) Math.round(cos * (getWidth() / 2) + sin * (getDepth() / 2));
		int zc = (int) Math.round(-sin * (getWidth() / 2) + cos * (getDepth() / 2));
		src.setIfExists((int) 
				Math.round(cos * (x - xc) + sin * (z - zc)) - xc, 
				y, 
				(int) Math.round(-sin * (x - xc) + cos * (z - zc)) - zc, t);
	}

	@Override
	public T getRaw(int x, int y, int z)
	{
		int xc = (int) Math.round(cos * (getWidth() / 2) + sin * (getDepth() / 2));
		int zc = (int) Math.round(-sin * (getWidth() / 2) + cos * (getDepth() / 2));
		return src.getIfExists(
				(int) Math.round(cos * (x - xc) + sin * (z - zc)) - xc,
				y,
				(int) Math.round(-sin * (x - xc) + cos * (z - zc)) - zc
				);
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
