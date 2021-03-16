package art.arcane.quill;

import art.arcane.quill.execution.J;
import art.arcane.quill.logging.L;
import art.arcane.quill.math.Profiler;
import art.arcane.quill.service.QuillService;
import art.arcane.quill.service.IService;
import java.lang.reflect.InvocationTargetException;

public class Quill
{
	public static Class<? extends QuillService> delegateClass = null;
	public static QuillService delegate = null;
	public static String DIR = System.getenv("APPDATA") + "/Shuriken";
	public static final Profiler profiler = new Profiler();
	public static final ServiceManager serviceManager = new ServiceManager();
	private static boolean gracefulShutdown = false;
	private static boolean shutdownHooks = false;

	public static void start(String[] a) {
		for (StackTraceElement i : Thread.currentThread().getStackTrace()) {
			try {
				Class<? extends QuillService> s = (Class<? extends QuillService>) Class.forName(i.getClassName());
				if (QuillService.class.isAssignableFrom(s)) {
					start(s, a);
					return;
				}
			} catch (Throwable e) {
				L.ex(e);
				L.f("Failed to find a service to start from!");
			}
		}
	}

	public static void start(Class<? extends QuillService> service, String[] a) {
		if (delegate != null) {
			crashStack("Service attempted to start when an existing delegate was already running!");
			return;
		}

		try {
			delegateClass = service;
			delegate = QuillService.initializeConfigured(service);
			assert delegate != null;
		} catch (Throwable e) {
			L.ex(e);
			crash("Failed to initialize Chimera Service Delegate Class");
			return;
		}

		try {
			delegate.startService();
		} catch (Throwable e) {
			L.ex(e);
			crash("Failed to start Chimera Service Delegate onEnable");
			return;
		}

		if(!shutdownHooks)
		{
			shutdownHooks = true;
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				if(!gracefulShutdown)
				{
					L.w("Non-Graceful shutdown detected... (are we crashing?) Attempting to shutdown properly");
					try
					{
						shutdown();
					}

					catch(Throwable e)
					{
						L.ex(e);
					}
				}

				else
				{
					L.i(Quill.delegateClass.getSimpleName() + " has Gracefully Shutdown");
				}
				L.flush();
			}));
		}
	}

	public static void shutdown() {
		try {
			delegate.stopService();
		} catch (Throwable e) {
			L.ex(e);
		}

		L.flush();
		gracefulShutdown = true;
		System.exit(0);
	}

	public static void crash() {
		crash("¯\\_(ツ)_/¯");
	}

	public static void crashStack(String message) {
		J.printStack(message);
		crash(message);
	}

	public static void crash(String message) {
		L.f("Chimera Service Crash: " + message);
		L.flush();
		System.exit(1);
	}

	public static String getDelegateModuleName() {
		return delegateClass.getCanonicalName().split("\\Q.\\E")[3];
	}

	public static <T extends IService> T getService(Class<? extends T> c)
	{
		try
		{
			return serviceManager.getService(c);
		}

		catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
