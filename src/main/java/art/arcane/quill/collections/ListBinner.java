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
 * Takes an input and splits it across mutiple lists. Checks list counts in the
 * event of changes in one of the 'bins'
 * 
 * @author cyberpwn
 *
 * @param <T>
 *            the type of item being filtered
 */
public abstract class ListBinner<T>
{
	/**
	 * Return the total amount of bins that can receive
	 * 
	 * @return the bin count
	 */
	public abstract int getBinCount();

	/**
	 * Return the amount of items in the given bin (by index)
	 * 
	 * @param binNumber
	 *            the bin index
	 * @return the bin count of items at the given index
	 */
	public abstract int getBinSize(int binNumber);

	/**
	 * This is called when you add an item to a bin, add the given item (t) to this
	 * bin
	 * 
	 * @param binNumber
	 *            the bin number to add T to
	 * @param t
	 *            the object to add to the bin specified
	 */
	public abstract void addToBin(int binNumber, T t);

	public int getMinimumBin()
	{
		int bin = -1;
		int cap = Integer.MAX_VALUE;
		int bc, i;

		for(i = 0; i < getBinCount(); i++)
		{
			bc = getBinSize(i);

			if(bc < cap)
			{
				cap = bc;
				bin = i;
			}
		}

		return bin;
	}

	/**
	 * Filter a new T through to a bin
	 * 
	 * @param t
	 *            the item
	 */
	public void add(T t)
	{
		addToBin(getMinimumBin(), t);
	}
}
