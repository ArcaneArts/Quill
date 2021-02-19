package art.arcane.quill.random.noise.stream.sources;


import art.arcane.quill.collections.functional.Function2;
import art.arcane.quill.collections.functional.Function3;
import art.arcane.quill.random.noise.stream.BasicStream;
import art.arcane.quill.random.noise.stream.interpolation.Interpolated;

public class FunctionStream<T> extends BasicStream<T>
{
	private Function2<Double, Double, T> f2;
	private Function3<Double, Double, Double, T> f3;
	private Interpolated<T> helper;

	public FunctionStream(Function2<Double, Double, T> f2, Function3<Double, Double, Double, T> f3, Interpolated<T> helper)
	{
		super();
		this.f2 = f2;
		this.f3 = f3;
		this.helper = helper;
	}

	@Override
	public double toDouble(T t)
	{
		return helper.toDouble(t);
	}

	@Override
	public T fromDouble(double d)
	{
		return helper.fromDouble(d);
	}

	@Override
	public T get(double x, double z)
	{
		return f2.apply(x, z);
	}

	@Override
	public T get(double x, double y, double z)
	{
		return f3.apply(x, y, z);
	}
}
