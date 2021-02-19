package art.arcane.quill.random;

@FunctionalInterface
public interface NoiseInjector
{
	public double[] combine(double src, double value);
}
