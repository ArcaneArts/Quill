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
 * Represents a keypair
 * @author cyberpwn
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class KeyPair<K, V>
{
	private K k;
	private V v;

	/**
	 * Create a keypair
	 * @param k the key
	 * @param v the value
	 */
	public KeyPair(K k, V v)
	{
		this.k = k;
		this.v = v;
	}

	public K getK()
	{
		return k;
	}

	public void setK(K k)
	{
		this.k = k;
	}

	public V getV()
	{
		return v;
	}

	public void setV(V v)
	{
		this.v = v;
	}
}
