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


import java.io.Serializable;

/**
 * A Biset
 *
 * @author cyberpwn
 *
 * @param <A>
 *            the first object type
 * @param <B>
 *            the second object type
 */
public class KBiset<A, B> implements Serializable
{
	private static final long serialVersionUID = 1L;
	private A a;
	private B b;

	/**
	 * Create a new Biset
	 *
	 * @param a
	 *            the first object
	 * @param b
	 *            the second object
	 */
	public KBiset(A a, B b)
	{
		this.a = a;
		this.b = b;
	}

	/**
	 * Get the object of the type A
	 *
	 * @return the first object
	 */
	public A getA()
	{
		return a;
	}

	/**
	 * Set the first object
	 *
	 * @param a
	 *            the first object A
	 */
	public void setA(A a)
	{
		this.a = a;
	}

	/**
	 * Get the second object
	 *
	 * @return the second object
	 */
	public B getB()
	{
		return b;
	}

	/**
	 * Set the second object
	 *
	 * @param b
	 */
	public void setB(B b)
	{
		this.b = b;
	}
}
