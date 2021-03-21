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
import art.arcane.quill.collections.KMap;
import art.arcane.quill.collections.functional.Consumer4;
import art.arcane.quill.collections.hunk.Hunk;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class MappedHunk<T> extends StorageHunk<T> implements Hunk<T>
{
	private final KMap<Integer, T> data;

	public MappedHunk(int w, int h, int d)
	{
		super(w, h, d);
		data = new KMap<>();
	}

	@Override
	public void setRaw(int x, int y, int z, T t)
	{
		if(t == null)
		{
			data.remove(index(x,y,z));
			return;
		}

		data.put(index(x, y, z), t);
	}

	private Integer index(int x, int y, int z)
	{
		return (z * getWidth() * getHeight()) + (y * getWidth()) + x;
	}

	@Override
	public synchronized Hunk<T> iterateSync(Consumer4<Integer, Integer, Integer, T> c)
	{
		int idx, z;

		for(Map.Entry<Integer, T> g : data.entrySet())
		{
			idx = g.getKey();
			z = idx / (getWidth() * getHeight());
			idx -= (z * getWidth() * getHeight());
			c.accept(idx % getWidth(), idx / getWidth(), z, g.getValue());
		}

		return this;
	}

	@Override
	public T getRaw(int x, int y, int z)
	{
		return data.get(index(x, y, z));
	}
}
