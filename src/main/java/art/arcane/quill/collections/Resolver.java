package art.arcane.quill.collections;

@FunctionalInterface
public interface Resolver<K, V>
{
	public V resolve(K k);
}
