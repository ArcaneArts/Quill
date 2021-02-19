package art.arcane.quill.cache;

public interface Multicache
{
    @SuppressWarnings("hiding")
	public <V> Cache<V> getCache(int id);

    @SuppressWarnings("hiding")
	public <V> Cache<V> createCache();
}
	