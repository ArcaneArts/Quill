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

import art.arcane.quill.format.Form;
import art.arcane.quill.logging.L;
import art.arcane.quill.math.PSW;
import art.arcane.quill.math.RollingSequence;

/**
 * Not particularly efficient or perfectly accurate but is great at fast thread switching detection
 * @author dan
 *
 */
public class ThreadMonitor extends Thread
{
	private Thread monitor;
	private boolean running;
	private State lastState;
	private ChronoLatch cl;
	private PSW st;
	int cycles = 0;
	private RollingSequence sq = new RollingSequence(3);
	
	private ThreadMonitor(Thread monitor)
	{
		running = true;
		st = PSW.start();
		this.monitor = monitor;
		lastState = State.NEW;
		cl = new ChronoLatch(1000);
		start();
	}
	
	public void run()
	{
		while(running)
		{
			try {
				Thread.sleep(0);
				State s = monitor.getState();
				if(lastState != s)
				{
					cycles++;
					pushState(s);
				}
				
				lastState = s;
				
				if(cl.flip())
				{
					L.i("Cycles: " + Form.f(cycles) + " (" + Form.duration(sq.getAverage(), 2) + ")");
					L.flush();
				}
			}
			
			catch(Throwable e)
			{
				running = false;
				break;
			}
		}
	}
	
	public void pushState(State s)
	{
		if(s != State.RUNNABLE)
		{
			if(st != null)
			{
				sq.put(st.getMilliseconds());
			}
		}
		
		else
		{

			st = PSW.start();
		}
	}
	
	public void unbind()
	{
		running = false;
	}
	
	public static ThreadMonitor bind(Thread monitor)
	{
		return new ThreadMonitor(monitor);
	}
}
