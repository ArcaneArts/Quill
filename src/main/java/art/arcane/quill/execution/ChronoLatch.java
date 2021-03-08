package art.arcane.quill.execution;

public class ChronoLatch {
	private long interval;
	private long since;

	public static Runnable wrap(ChronoLatch l, Runnable f) {
		return () -> {
			if (l.flip()) {
				f.run();
			}
		};
	}

	public static Runnable wrap(long interval, Runnable f) {
		return wrap(new ChronoLatch(interval), f);
	}

	public ChronoLatch(long interval, boolean openedAtStart) {
		this.interval = interval;
		since = System.currentTimeMillis() - (openedAtStart ? interval * 2 : 0);
	}

	public ChronoLatch(long interval) {
		this(interval, true);
	}

	public boolean flip() {
		if (System.currentTimeMillis() - since > interval) {
			since = System.currentTimeMillis();
			return true;
		}

		return false;
	}

	public Runnable wrap(Runnable r) {
		return wrap(this, r);
	}
}
