package art.arcane.quill.random.noise.stream.convert;


import art.arcane.quill.collections.functional.Function4;
import art.arcane.quill.random.noise.stream.BasicStream;
import art.arcane.quill.random.noise.stream.ProceduralStream;

public class AwareConversionStream3D<T, V> extends BasicStream<V>
{
	private final ProceduralStream<T> stream;
	private final Function4<T, Double, Double, Double, V> converter;

	public AwareConversionStream3D(ProceduralStream<T> stream, Function4<T, Double, Double, Double, V> converter)
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


	}@Override
	public ProceduralStream<?> getSource() {
		return stream;
	}

	@Override
	public V get(double x, double z)
	{
		return converter.apply(stream.get(x, z), x, 0D, z);
	}

	@Override
	public V get(double x, double y, double z)
	{
		return converter.apply(stream.get(x, y, z), x, y, z);
	}
}
