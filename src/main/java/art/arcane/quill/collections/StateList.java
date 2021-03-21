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

public class StateList
{
	private final KList<String> states;

	public StateList(String... states)
	{
		this.states = new KList<String>(states);

		if(getBits() > 64)
		{
			throw new RuntimeException("StateLists cannot exceed 64 bits! You are trying to use " + getBits() + " bits!");
		}
	}

	public long max()
	{
		return (long) (Math.pow(2, getBits()) - 1);
	}

	public KList<String> getEnabled(long list)
	{
		KList<String> f = new KList<>();

		for(String i : states)
		{
			if(is(list, i))
			{
				f.add(i);
			}
		}

		return f;
	}

	public long of(String... enabledStates)
	{
		long b = 0;

		for(String i : enabledStates)
		{
			b |= getBit(i);
		}

		return b;
	}

	public long set(long list, String state, boolean enabled)
	{
		long bit = getBit(state);
		boolean is = is(list, state);

		if(enabled && !is)
		{
			return list | bit;
		}

		else if(!enabled && is)
		{
			return list ^ bit;
		}

		return list;
	}

	public boolean is(long list, String state)
	{
		long bit = getBit(state);

		return bit > 0 && (list & bit) == bit;
	}

	public boolean hasBit(String state)
	{
		return getBit(state) > 0;
	}

	public long getBit(String state)
	{
		return getBit(states.indexOf(state));
	}

	public long getBit(int index)
	{
		return (long) (index < 0 ? -1 : Math.pow(2, index));
	}

	public int getBytes()
	{
		return getBits() == 0 ? 0 : ((getBits() >> 2) + 1);
	}

	public int getBits()
	{
		return states.size();
	}
}
