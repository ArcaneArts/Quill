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

public class Wrapper<T>
{
	private T t;

	public Wrapper(T t)
	{
		set(t);
	}

	public void set(T t)
	{
		this.t = t;
	}

	public T get()
	{
		return t;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((t == null) ? 0 : t.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
		{
			return true;
		}
		if(obj == null)
		{
			return false;
		}
		if(!(obj instanceof Wrapper))
		{
			return false;
		}

		Wrapper<?> other = (Wrapper<?>) obj;
		if(t == null)
		{
			if(other.t != null)
			{
				return false;
			}
		}
		else if(!t.equals(other.t))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		if(t != null)
		{
			return get().toString();
		}

		return super.toString() + " (null)";
	}
}
