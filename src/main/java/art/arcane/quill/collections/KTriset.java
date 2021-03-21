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

@SuppressWarnings("hiding")
public class KTriset<A, B, C> implements Serializable
{
	private static final long serialVersionUID = 1912465707826963942L;
	private A a;
	private B b;
	private C c;

	public KTriset(A a, B b, C c)
	{
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public A getA()
	{
		return a;
	}

	public void setA(A a)
	{
		this.a = a;
	}

	public B getB()
	{
		return b;
	}

	public void setB(B b)
	{
		this.b = b;
	}

	public C getC()
	{
		return c;
	}

	public void setC(C c)
	{
		this.c = c;
	}
}
