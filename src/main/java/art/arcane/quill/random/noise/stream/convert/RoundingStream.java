package art.arcane.quill.random.noise.stream.convert;


import art.arcane.quill.random.noise.stream.BasicStream;
import art.arcane.quill.random.noise.stream.ProceduralStream;

public class RoundingStream extends BasicStream<Integer>
{
	private final ProceduralStream<?> stream;

	public RoundingStream(ProceduralStream<?> stream)
	{
		super();
		this.stream = stream;
	}

	@Override
	public double toDouble(Integer t)
	{
		return t.doubleValue();
	}

	@Override
	public Integer fromDouble(double d)
	{
		return (int) Math.round(d);
	}

	private int round(double v)
	{
		return (int) Math.round(v);
	}

	@Override
	public Integer get(double x, double z)
	{
		return round(stream.getDouble(x, z));
	}

	@Override
	public Integer get(double x, double y, double z)
	{
		return round(stream.getDouble(x, y, z));
	}
}
