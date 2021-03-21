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

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class KSet<T> extends HashSet<T>
{
	private static final long serialVersionUID = 1L;

	public KSet()
	{
		super();
	}

	public KSet(Collection<? extends T> c)
	{
		super(c);
	}

	public KSet(int initialCapacity, float loadFactor)
	{
		super(initialCapacity, loadFactor);
	}

	public KSet(int initialCapacity)
	{
		super(initialCapacity);
	}

	public String toString(String split)
	{
		if (isEmpty())
		{
			return "";
		}

		if (size() == 1)
		{
			return iterator().next().toString();
		}

		StringBuilder b = new StringBuilder();
		Iterator<T> g = iterator();

		while (g.hasNext())
		{
			b.append(split + g.next());
		}

		return b.toString().substring(split.length());
	}
}
