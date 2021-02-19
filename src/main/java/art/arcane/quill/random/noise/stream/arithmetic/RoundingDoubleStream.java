package art.arcane.quill.random.noise.stream.arithmetic;

import art.arcane.quill.random.noise.stream.BasicStream;
import art.arcane.quill.random.noise.stream.ProceduralStream;

public class RoundingDoubleStream extends BasicStream<Double>
{
	private final ProceduralStream<?> stream;

	public RoundingDoubleStream(ProceduralStream<?> stream)
	{
		super();
		this.stream = stream;
	}

	@Override
	public double toDouble(Double t)
	{
		return t;
	}

	@Override
	public Double fromDouble(double d)
	{
		return (double) Math.round(d);
	}

	private double round(double v)
	{
		return Math.round(v);
	}

	@Override
	public Double get(double x, double z)
	{
		return round(stream.getDouble(x, z));
	}

	@Override
	public Double get(double x, double y, double z)
	{
		return round(stream.getDouble(x, y, z));
	}

}
