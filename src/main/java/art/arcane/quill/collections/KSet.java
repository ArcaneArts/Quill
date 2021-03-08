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
