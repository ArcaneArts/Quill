package art.arcane.quill.random;
@FunctionalInterface
public interface NoiseProvider
{
	public double noise(double x, double z);
}