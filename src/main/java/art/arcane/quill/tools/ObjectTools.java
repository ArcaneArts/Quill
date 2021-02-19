package art.arcane.quill.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ObjectTools
{
	public static void shove(Object data, Object into) throws BlackMagicException
	{
		if(!data.getClass().equals(into.getClass()))
		{
			throw new BlackMagicException("Classes must be the same");
		}

		for(Field i : data.getClass().getDeclaredFields())
		{
			if(Modifier.isStatic(i.getModifiers()))
			{
				continue;
			}

			i.setAccessible(true);

			try
			{
				i.set(into, i.get(data));
			}

			catch(IllegalArgumentException | IllegalAccessException e)
			{
				e.printStackTrace();
				throw new BlackMagicException("Reflection error");
			}
		}
	}
}
