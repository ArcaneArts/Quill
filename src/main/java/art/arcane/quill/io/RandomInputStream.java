package art.arcane.quill.io;

import art.arcane.quill.random.RNG;

import java.io.IOException;
import java.io.InputStream;

public class RandomInputStream extends InputStream
{
	private RNG rng;
	
	public RandomInputStream(String seed)
	{
		rng = new RNG(seed);
	}
	
	public RandomInputStream(long seed)
	{
		rng = new RNG(seed);
	}
	
	public RandomInputStream()
	{
		rng = new RNG();
	}
	
	@Override
	public int read() throws IOException
	{
		return (int) (rng.imax() % 256);
	}
}
