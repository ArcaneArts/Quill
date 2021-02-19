package art.arcane.quill.collections.hunk.storage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import art.arcane.quill.collections.hunk.Hunk;

import java.util.concurrent.atomic.AtomicIntegerArray;

@Data
@EqualsAndHashCode(callSuper = false)
public class AtomicIntegerHunk extends StorageHunk<Integer> implements Hunk<Integer>
{
	private final AtomicIntegerArray data;

	public AtomicIntegerHunk(int w, int h, int d)
	{
		super(w, h, d);
		data = new AtomicIntegerArray(w * h * d);
	}

	@Override
	public boolean isAtomic()
	{
		return true;
	}

	@Override
	public void setRaw(int x, int y, int z, Integer t)
	{
		data.set(index(x, y, z), t);
	}

	@Override
	public Integer getRaw(int x, int y, int z)
	{
		return data.get(index(x, y, z));
	}

	private int index(int x, int y, int z)
	{
		return (z * getWidth() * getHeight()) + (y * getWidth()) + x;
	}
}
