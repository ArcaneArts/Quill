package art.arcane.quill.collections.functional;

public interface NastyFuture<R>
{
	public R run() throws Throwable;
}
