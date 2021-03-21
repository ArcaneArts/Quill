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

package art.arcane.quill.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Reflect
{
	public static Reflect as(Object instance)
	{
		return new Reflect(instance);
	}
	
	private int throwExceptions;
	private Class<?> c;
	private Object instance;
	private Object oinstance;
	
	public Reflect(Object instance)
	{
		this.throwExceptions = 0;
		this.instance = instance;
		
		if(instance instanceof Class)
		{
			c = (Class<?>) instance;
			oinstance = null;
		}
		
		else
		{
			c = instance.getClass();
			oinstance = instance;
		}
	}
	
	public Reflect throwExceptions()
	{
		throwExceptions = 2;
		return this;
	}
	
	public Reflect printExceptions()
	{
		throwExceptions = 1;
		return this;
	}
	
	public Reflect noExceptions()
	{
		throwExceptions = 0;
		return this;
	}
	
	public Field field(String field)
	{
		try {
			Field f = c.getField(field);
			
			if(!f.isAccessible())
			{
				f.setAccessible(true);
			}
			
			return f;
		} catch (Throwable e) {
			handle(e);
		}
		
		return null;
	}
	
	public Method method(String method)
	{
		try {
			Method f = c.getMethod(method);
			
			if(!f.isAccessible())
			{
				f.setAccessible(true);
			}
			
			return f;
		} catch (Throwable e) {
			handle(e);
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T invoke(String method, Object... parameters)
	{
		try {
			return (T) method(method).invoke(oinstance, parameters);
		} catch (Throwable e) {
			handle(e);
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String field)
	{
		try {
			return (T) field(field).get(oinstance);
		} catch (Throwable e) {
			handle(e);
		}
		
		return null;
	}
	
	public Reflect set(String field, Object value)
	{
		try {
			field(field).set(oinstance, value);
		} catch (Throwable e) {
			handle(e);
		}
		
		return this;
	}

	private void handle(Throwable e) {
		if(throwExceptions == 0)
		{
			return;
		}
		
		if(throwExceptions == 1)
		{
			e.printStackTrace();
		}
		
		if(throwExceptions == 2)
		{
			throw new RuntimeException(e);
		}
	}

	public Object getInstance()
	{
		return instance;
	}
}
