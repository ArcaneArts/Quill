package art.arcane.quill.events;

import art.arcane.quill.collections.KList;
import art.arcane.quill.collections.KMap;
import art.arcane.quill.logging.L;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class EventRegistry
{
	private static final KMap<Class<? extends Event>, KList<EventTarget>> registry = new KMap<>();

	public static void fire(Event event)
	{
		Class<? extends Event> c = event.getClass();

		if(registry.containsKey(c))
		{
			for(EventTarget i : registry.get(c))
			{
				try
				{
					i.invoke(event);
				}

				catch(Throwable e)
				{
					L.f("Failed to invoke event " + event.toString() + " on " + i.getInstance().toString() + "." + i.getMethod().getName() + "()");
					L.ex(e);
				}
			}
		}
	}

	public static void unregisterAll()
	{
		registry.clear();
	}

	public static void unregister(Object instance)
	{
		for(Method i : instance.getClass().getDeclaredMethods())
		{
			if(i.isAnnotationPresent(EventHandler.class))
			{
				if(Modifier.isStatic(i.getModifiers()))
				{
					continue;
				}

				if(Modifier.isAbstract(i.getModifiers()))
				{
					continue;
				}

				if(!i.isAccessible())
				{
					i.setAccessible(true);
				}

				if(i.getParameterCount() != 1)
				{
					continue;
				}

				Class<?> c = i.getParameters()[0].getType();

				if(!Event.class.isAssignableFrom(c))
				{
					continue;
				}

				@SuppressWarnings("unchecked")
				Class<? extends Event> e = (Class<? extends Event>) c;

				if(!registry.containsKey(e))
				{
					continue;
				}

				for(EventTarget j : registry.get(e).copy())
				{
					if(j.getMethod().equals(i) && j.getInstance().equals(instance))
					{
						registry.get(e).remove(j);
					}
				}

				if(registry.get(e).isEmpty())
				{
					registry.remove(e);
				}
			}
		}
	}

	public static void register(Object instance)
	{
		for(Method i : instance.getClass().getDeclaredMethods())
		{
			if(i.isAnnotationPresent(EventHandler.class))
			{
				if(Modifier.isStatic(i.getModifiers()))
				{
					L.w("[Event Bus] Cannot register method " + i.getName() + "(...) in " + instance.getClass().getCanonicalName() + " since it is static");
					continue;
				}

				if(Modifier.isAbstract(i.getModifiers()))
				{
					L.w("[Event Bus] Cannot register method " + i.getName() + "(...) in " + instance.getClass().getCanonicalName() + " since it is abstract");
					continue;
				}

				if(!i.isAccessible())
				{
					i.setAccessible(true);
				}

				if(i.getParameterCount() != 1)
				{
					L.w("[Event Bus] Cannot register method " + i.getName() + "(XXX) in " + instance.getClass().getCanonicalName() + " since it does not have 1 parameter (event)");
					continue;
				}

				Class<?> c = i.getParameters()[0].getType();

				if(!Event.class.isAssignableFrom(c))
				{
					L.w("[Event Bus] Cannot register method " + i.getName() + "(XXX) in " + instance.getClass().getCanonicalName() + " since it's parameter does not extend Event");
					continue;
				}

				@SuppressWarnings("unchecked")
				Class<? extends Event> e = (Class<? extends Event>) c;

				if(!registry.containsKey(e))
				{
					registry.put(e, new KList<>());
				}

				registry.get(e).add(new EventTarget(instance, i));
			}
		}
	}
}
