package art.arcane.quill.execution.parallel;

import art.arcane.quill.collections.hunk.Hunk;

public interface BurstedHunk<T> extends Hunk<T>
{
	public int getOffsetX();

	public int getOffsetY();

	public int getOffsetZ();
}
