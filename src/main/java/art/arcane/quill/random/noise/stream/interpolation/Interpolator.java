package art.arcane.quill.random.noise.stream.interpolation;


import art.arcane.quill.random.noise.stream.ProceduralStream;

public interface Interpolator<T>
{
	@SuppressWarnings("unchecked")
	default InterpolatorFactory<T> into()
	{
		if(this instanceof ProceduralStream)
		{
			return new InterpolatorFactory<T>((ProceduralStream<T>) this);
		}

		return null;
	}
}
