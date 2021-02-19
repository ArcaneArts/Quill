package art.arcane.quill.random.noise.stream;


import art.arcane.quill.collections.KList;

public interface Significance<T>
{
	public KList<T> getFactorTypes();

	public double getSignificance(T t);

	public T getMostSignificantType();
}
