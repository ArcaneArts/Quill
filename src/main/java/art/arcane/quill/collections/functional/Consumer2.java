package art.arcane.quill.collections.functional;

@FunctionalInterface
public interface Consumer2<A, B>
{
	public void accept(A a, B b);
}
