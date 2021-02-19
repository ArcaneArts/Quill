package art.arcane.quill.collections.functional;

@FunctionalInterface
public interface Function2<A, B, R>
{
	public R apply(A a, B b);
}
