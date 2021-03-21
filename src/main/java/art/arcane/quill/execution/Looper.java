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

import art.arcane.quill.logging.L;

public abstract class Looper extends Thread
{
	public void run()
	{
		while(!interrupted())
		{
			try
			{
				long m = loop();
				
				if(m < 0)
				{
					break;
				}
				
				Thread.sleep(m);
			}
			
			catch(InterruptedException e)
			{
				break;
			}
			
			catch(Throwable e)
			{
				e.printStackTrace();
			}
		}
		
		L.i("Thread " + getName() + " Shutdown.");
	}
	
	protected abstract long loop();
}
