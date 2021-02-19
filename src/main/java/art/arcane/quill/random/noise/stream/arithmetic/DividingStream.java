package art.arcane.quill.random.noise.stream.arithmetic;

import art.arcane.quill.collections.functional.Function2;
import art.arcane.quill.collections.functional.Function3;
import art.arcane.quill.random.noise.stream.BasicStream;
import art.arcane.quill.random.noise.stream.ProceduralStream;

public class DividingStream<T> extends BasicStream<T> implements ProceduralStream<T>
{
	private final Function3<Double, Double, Double, Double> add;

	public DividingStream(ProceduralStream<T> stream, Function3<Double, Double, Double, Double> add)
	{
		super(stream);
		this.add = add;
	}

	public DividingStream(ProceduralStream<T> stream, Function2<Double, Double, Double> add)
	{
		this(stream, (x, y, z) -> add.apply(x, z));
	}

	public DividingStream(ProceduralStream<T> stream, double add)
	{
		this(stream, (x, y, z) -> add);
	}

	@Override
	public double toDouble(T t)
	{
		return getTypedSource().toDouble(t);
	}

	@Override
	public T fromDouble(double d)
	{
		return getTypedSource().fromDouble(d);
	}

	@Override
	public T get(double x, double z)
	{
		return fromDouble(getTypedSource().getDouble(x, z) / add.apply(x, 0D, z));
	}

	@Override
	public T get(double x, double y, double z)
	{
		return fromDouble(getTypedSource().getDouble(x, y, z) / add.apply(x, y, z));
	}
}
