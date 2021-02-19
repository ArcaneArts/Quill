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
