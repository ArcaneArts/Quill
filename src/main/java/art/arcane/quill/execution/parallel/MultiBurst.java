package art.arcane.quill.execution.parallel;

import art.arcane.quill.logging.L;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class MultiBurst
{
	public static MultiBurst burst = new MultiBurst(Runtime.getRuntime().availableProcessors());
	private final ExecutorService service;
	private ExecutorService syncService;
	private int tid;

	public MultiBurst(int tc)
	{
		service = tc == 1 ? Executors.newSingleThreadExecutor(r -> {
			Thread t = new Thread(r);
			t.setName("Quill Burst Executor (SC)");
			t.setPriority(Thread.MAX_PRIORITY);
			t.setUncaughtExceptionHandler((et, e) ->
			{
				L.f("Exception encountered in " + et.getName());
				L.ex(e);
			});

			return t;
		}) : Executors.newFixedThreadPool(tc, r -> {
			tid++;
			Thread t = new Thread(r);
			t.setName("Quill Burst Executor " + tid);
			t.setPriority(Thread.MAX_PRIORITY);
			t.setUncaughtExceptionHandler((et, e) ->
			{
				L.f("Exception encountered in " + et.getName());
				L.ex(e);
			});

			return t;
		});
	}

	public void burst(Runnable... r)
	{
		burst(r.length).queue(r).complete();
	}

	public void sync(Runnable... r)
	{
		for(Runnable i : r)
		{
			i.run();
		}
	}

	public BurstExecutor burst(int estimate)
	{
		return new BurstExecutor(service, estimate);
	}

	public BurstExecutor burst()
	{
		return burst(16);
	}

	public void lazy(Runnable o) {
		service.execute(o);
	}
}
