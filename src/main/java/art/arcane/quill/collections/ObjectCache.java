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

import java.util.function.Supplier;

public class ObjectCache<O>
{
	private boolean valid;
	private O o;
	private Supplier<O> s;

	public ObjectCache(Supplier<O> s)
	{
		valid = false;
		o = null;
		this.s = s;
	}

	public boolean isValid()
	{
		return valid && o != null;
	}

	public void invalidate()
	{
		valid = false;
	}

	public <PORN> O get()
	{
		if(o == null || !valid)
		{
			o = s.get();
			valid = true;
		}

		return (O) o;
	}
}
