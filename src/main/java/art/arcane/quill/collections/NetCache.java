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

public class NetCache<K, V>
{
	private final Resolver<K, V> resolver;
	private final KMap<K, V> cache;

	public NetCache(Resolver<K, V> resolver)
	{
		cache = new KMap<K, V>();
		this.resolver = resolver;
	}

	public KMap<K, V> cache()
	{
		return cache;
	}

	public void clear()
	{
		cache.clear();
	}

	public KList<K> k()
	{
		return cache.k();
	}

	public void put(K k, V v)
	{
		cache.put(k, v);
	}

	public boolean has(K k)
	{
		return cache.containsKey(k);
	}

	public void invalidate(K k)
	{
		cache.remove(k);
	}

	public V getReal(K k)
	{
		invalidate(k);
		return get(k);
	}

	public V get(K k)
	{
		if(!has(k))
		{
			put(k, resolver.resolve(k));
		}

		return cache.get(k);
	}
}
