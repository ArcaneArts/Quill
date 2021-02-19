package art.arcane.quill.random.noise;

import art.arcane.quill.random.RNG;

public class WhiteNoise implements NoiseGenerator
{
	private final FastNoise n;

	public WhiteNoise(long seed)
	{
		n = new FastNoise(new RNG(seed).imax());
	}

	public boolean isStatic()
	{
		return true;
	}

	private double f(double m)
	{
		return (m % 8192) * 1024;
	}

	@Override
	public double noise(double x)
	{
		return (n.GetWhiteNoise(f(x), 0d) / 2D) + 0.5D;
	}

	@Override
	public double noise(double x, double z)
	{
		return (n.GetWhiteNoise(f(x), f(z)) / 2D) + 0.5D;
	}

	@Override
	public double noise(double x, double y, double z)
	{
		return (n.GetWhiteNoise(f(x), f(y), f(z)) / 2D) + 0.5D;
	}
}
