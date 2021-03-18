package art.arcane.quill;

import art.arcane.quill.collections.KList;
import art.arcane.quill.collections.KMap;
import art.arcane.quill.collections.functional.NastyRunnable;
import art.arcane.quill.execution.J;
import art.arcane.quill.logging.L;
import art.arcane.quill.math.Profiler;
import art.arcane.quill.service.QuillService;

public class Quill
{
	private static KMap<String, String> crashMetrics = new KMap<>();
	public static Class<? extends QuillService> delegateClass = null;
	public static QuillService delegate = null;
	public static String DIR = System.getenv("APPDATA") + "/Shuriken";
	public static final Profiler profiler = new Profiler();
	public static int serviceIds = 0;
	private static boolean gracefulShutdown = false;
	private static boolean shutdownHooks = false;
	private static boolean postRan = false;
	private static final KList<NastyRunnable> post = new KList<>();

	public static void postJob(NastyRunnable r)
	{
		if(postRan)
		{
			Quill.crashStack("Cannot queue post jobs after startup has completed or during post execution. You can only use this during onEnable() or before.");
			return;
		}

		post.add(r);
	}

	public static void logState(String key, String v)
	{
		crashMetrics.put(key, v);
	}

	public static void runPost()
	{
		if(postRan)
		{
			Quill.crashStack("Cannot execute post jobs twice.");
			return;
		}

		postRan = true;

		post.forEach((i) -> {
			try
			{
				i.run();
			}

			catch(Throwable e)
			{
				L.ex(e);
				Quill.crashStack("Failed to execute post job (see above)");
			}
		});

		L.v("Ran " + post.size() + " Post Job(s)");

		post.clear();
	}

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
		shutdownGracefully();
	}

	public static void crash() {
		crash("¯\\_(ツ)_/¯");
	}

	public static void crash(Throwable e) {
		printErr("¯\\_(ツ)_/¯", e);
		shutdown(1);
	}

	public static void crash(String message, Throwable e) {
		printErr(message, e);
		shutdown(1);
	}

	public static void crashStack(String message) {
		printErr(message);
		shutdown(1);
	}

	public static void crash(String message) {
		printErr(message);
		shutdown(1);
	}

	private static void printErr(String message) {
		printErr(message, new RuntimeException(message));
	}

	private static void printErr(String message, Throwable e) {
		L.flush();
		L.f("===============================================================================================");
		L.flush();
		L.f("System Crash: " + message);
		L.f("-----------------------------------------------------------------------------------------------");
		L.flush();
		L.ex(e);
		L.f("-----------------------------------------------------------------------------------------------");
		L.flush();
		L.f("Read above for any stack traces. Below is additional state info before");
		L.f("the crash happened.");
		L.f("-----------------------------------------------------------------------------------------------");
		for(String i : crashMetrics.k())
		{
			L.f(i + ": " + crashMetrics.get(i));
		}
		L.f("===============================================================================================");
		L.flush();
	}

	public static void shutdownGracefully()
	{
		L.flush();
		L.shutdown();
	}

	public static void shutdown(int code)
	{
		shutdownGracefully();
		System.exit(code);
	}

	public static String getDelegateModuleName() {
		return delegateClass.getCanonicalName().split("\\Q.\\E")[3];
	}
}
