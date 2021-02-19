package art.arcane.quill.random.noise;

import art.arcane.quill.random.RNG;

@FunctionalInterface
public interface CNGFactory 
{
	CNG create(RNG seed);
}
