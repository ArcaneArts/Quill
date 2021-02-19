package art.arcane.quill.random.noise;

import art.arcane.quill.random.RNG;

public class FractalFBMSimplexNoise implements NoiseGenerator, OctaveNoise
{
	private final FastNoiseDouble n;

	public FractalFBMSimplexNoise(long seed)
	{
		this.n = new FastNoiseDouble(new RNG(seed).lmax());
		n.setFractalOctaves(1);
		n.setFractalType(FastNoiseDouble.FractalType.FBM);
	}

	public double f(double v)
	{
		return (v / 2D) + 0.5D;
	}

	@Override
	public double noise(double x)
	{
		return f(n.GetSimplexFractal(x, 0d));
	}

	@Override
	public double noise(double x, double z)
	{
		return f(n.GetSimplexFractal(x, z));
	}

	@Override
	public double noise(double x, double y, double z)
	{
		return f(n.GetSimplexFractal(x, y, z));
	}

	@Override
	public void setOctaves(int o)
	{
		n.setFractalOctaves(o);
	}
}
