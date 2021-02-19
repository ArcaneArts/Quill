package art.arcane.quill.random.noise;

@FunctionalInterface
public interface NoiseFactory 
{
	NoiseGenerator create(long seed);
}
