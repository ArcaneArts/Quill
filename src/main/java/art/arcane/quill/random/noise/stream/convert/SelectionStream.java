package art.arcane.quill.random.noise.stream.convert;

import art.arcane.quill.random.noise.stream.BasicStream;
import art.arcane.quill.random.noise.stream.ProceduralStream;

import java.util.List;


public class SelectionStream<T> extends BasicStream<T>
{
	private final ProceduralStream<Integer> stream;
	private final T[] options;

	public SelectionStream(ProceduralStream<?> stream, T[] options)
	{
		super();
		this.stream = stream.fit(0, options.length - 1).round();
		this.options = options;
	}

	@SuppressWarnings("unchecked")
	public SelectionStream(ProceduralStream<?> stream, List<T> options)
	{
		this(stream, (T[]) options.toArray());
	}

	@Override
	public double toDouble(T t)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public T fromDouble(double d)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public T get(double x, double z)
	{
		if(options.length == 0)
		{
			return null;
		}

		return options[stream.get(x, z)];
	}

	@Override
	public T get(double x, double y, double z)
	{
		if(options.length == 0)
		{
			return null;
		}

		return options[stream.get(x, y, z)];
	}

}
