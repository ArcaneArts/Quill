package art.arcane.quill.collections.hunk.storage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import art.arcane.quill.collections.atomics.AtomicDoubleArray;
import art.arcane.quill.collections.hunk.Hunk;

@Data
@EqualsAndHashCode(callSuper = false)
public class AtomicDoubleHunk extends StorageHunk<Double> implements Hunk<Double>
{
	private final AtomicDoubleArray data;

	public AtomicDoubleHunk(int w, int h, int d)
	{
		super(w, h, d);
		data = new AtomicDoubleArray(w * h * d);
	}

	@Override
	public boolean isAtomic()
	{
		return true;
	}

	@Override
	public void setRaw(int x, int y, int z, Double t)
	{
		data.set(index(x, y, z), t);
	}

	@Override
	public Double getRaw(int x, int y, int z)
	{
		return data.get(index(x, y, z));
	}

	private int index(int x, int y, int z)
	{
		return (z * getWidth() * getHeight()) + (y * getWidth()) + x;
	}
}
