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

/**
 * Represents a number that can be finalized and be changed
 *
 * @author cyberpwn
 */
public class FinalInteger extends Wrapper<Integer>
{
	public FinalInteger(Integer t)
	{
		super(t);
	}

	/**
	 * Add to this value
	 *
	 * @param i
	 *            the number to add to this value (value = value + i)
	 */
	public void add(int i)
	{
		set(get() + i);
	}

	/**
	 * Subtract from this value
	 *
	 * @param i
	 *            the number to subtract from this value (value = value - i)
	 */
	public void sub(int i)
	{
		set(get() - i);
	}
}
