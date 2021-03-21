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

package art.arcane.quill.execution;

import art.arcane.quill.collections.Contained;
import art.arcane.quill.collections.KList;
import art.arcane.quill.collections.functional.Callback;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Chunker<T>
{
	private ExecutorService executor;
	private int threads;
	private int workload;
	private KList<T> q;

	public Chunker(KList<T> q)
	{
		this.q = q;
	}

	public Chunker<T> threads(int threads)
	{
		this.threads = threads;
		return this;
	}

	public Chunker<T> workload(int workload)
	{
		this.workload = workload;
		return this;
	}

	public void execute(Consumer<T> consumer, Callback<Double> progress, int progressInterval)
	{
		ChronoLatch cl = new ChronoLatch(progressInterval);
		Contained<Integer> consumed = new Contained<Integer>(0);
		executor = Executors.newFixedThreadPool(threads);
		int length = q.size();
		int remaining = length;

		while(remaining > 0)
		{
			int at = remaining;
			remaining -= (remaining > workload ? workload : remaining);
			int to = remaining;

			executor.submit(() ->
			{
				J.dofor(at, (i) -> i >= to, -1, (i) -> J.attempt(() -> consumer.accept(q.get(i))));
				consumed.mod((c) -> c += workload);
				J.doif(() -> progress != null && cl.flip(), () -> progress.run((double) consumed.get() / (double) length));
			});
		}

		executor.shutdown();
		J.attempt(() -> executor.awaitTermination(100, TimeUnit.HOURS));
	}
}
