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
import art.arcane.quill.collections.hunk.Hunk;

@Data
public abstract class StorageHunk<T> implements Hunk<T>
{
	private final int width;
	private final int height;
	private final int depth;

	public StorageHunk()
	{
		this(1,1,1);
	}

	public StorageHunk(int width, int height, int depth)
	{
		if(width <= 0 || height <= 0 || depth <= 0)
		{
			throw new RuntimeException("Unsupported size " + width + " " + height + " " + depth);
		}

		this.width = width;
		this.height = height;
		this.depth = depth;
	}

	@Override
	public abstract void setRaw(int x, int y, int z, T t);

	@Override
	public abstract T getRaw(int x, int y, int z);
}
