package art.arcane.quill.random.noise;

import art.arcane.quill.random.RNG;

public class FractalCubicNoise implements NoiseGenerator
{
	private final FastNoiseDouble n;

	public FractalCubicNoise(long seed)
	{
		this.n = new FastNoiseDouble(new RNG(seed).lmax());
		n.setFractalType(FastNoiseDouble.FractalType.Billow);
	}

	private double f(double n)
	{
		return (n / 2D) + 0.5D;
	}

	@Override
	public double noise(double x)
	{
		return f(n.GetCubicFractal(x, 0));
	}

	@Override
	public double noise(double x, double z)
	{
		return f(n.GetCubicFractal(x, z));
	}

	@Override
	public double noise(double x, double y, double z)
	{
		return f(n.GetCubicFractal(x, y, z));
	}
}
