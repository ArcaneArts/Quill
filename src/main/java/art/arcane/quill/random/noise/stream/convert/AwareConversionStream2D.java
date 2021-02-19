package art.arcane.quill.random.noise.stream.convert;


import art.arcane.quill.collections.functional.Function3;
import art.arcane.quill.random.noise.stream.BasicStream;
import art.arcane.quill.random.noise.stream.ProceduralStream;

public class AwareConversionStream2D<T, V> extends BasicStream<V>
{
	private final ProceduralStream<T> stream;
	private final Function3<T, Double, Double, V> converter;

	public AwareConversionStream2D(ProceduralStream<T> stream, Function3<T, Double, Double, V> converter)
	{
		super(null);
		this.stream = stream;
		this.converter = converter;
	}

	@Override
	public double toDouble(V t)
	{
		if(t instanceof Double)
		{
			return (Double) t;
		}

		return 0;
	}

	@Override
	public V fromDouble(double d)
	{
		return null;
	}

	@Override
	public ProceduralStream<?> getSource() {
		return stream;
	}

	@Override
	public V get(double x, double z)
	{
		return converter.apply(stream.get(x, z), x, z);
	}

	@Override
	public V get(double x, double y, double z)
	{
		return converter.apply(stream.get(x, y, z), x, z);
	}
}
