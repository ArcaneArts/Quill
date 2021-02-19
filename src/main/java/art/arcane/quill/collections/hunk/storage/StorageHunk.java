package art.arcane.quill.collections.hunk.storage;

import lombok.Data;
import art.arcane.quill.collections.hunk.Hunk;

@Data
public abstract class StorageHunk<T> implements Hunk<T>
{
	private final int width;
	private final int height;
	private final int depth;

	public StorageHunk()
	{
		this(1,1,1);
	}

	public StorageHunk(int width, int height, int depth)
	{
		if(width <= 0 || height <= 0 || depth <= 0)
		{
			throw new RuntimeException("Unsupported size " + width + " " + height + " " + depth);
		}

		this.width = width;
		this.height = height;
		this.depth = depth;
	}

	@Override
	public abstract void setRaw(int x, int y, int z, T t);

	@Override
	public abstract T getRaw(int x, int y, int z);
}
