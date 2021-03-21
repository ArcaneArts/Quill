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

package art.arcane.quill.collections.hunk.storage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import art.arcane.quill.collections.hunk.Hunk;

import java.util.concurrent.atomic.AtomicReferenceArray;

@Data
@EqualsAndHashCode(callSuper = false)
public class AtomicHunk<T> extends StorageHunk<T> implements Hunk<T>
{
	private final AtomicReferenceArray<T> data;

	public AtomicHunk(int w, int h, int d)
	{
		super(w, h, d);
		data = new AtomicReferenceArray<T>(w * h * d);
	}
	
	@Override
	public boolean isAtomic()
	{
		return true;
	}

	@Override
	public void setRaw(int x, int y, int z, T t)
	{
		data.set(index(x, y, z), t);
	}

	@Override
	public T getRaw(int x, int y, int z)
	{
		return data.get(index(x, y, z));
	}

	private int index(int x, int y, int z)
	{
		return (z * getWidth() * getHeight()) + (y * getWidth()) + x;
	}
}
