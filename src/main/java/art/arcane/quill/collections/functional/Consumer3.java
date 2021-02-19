package art.arcane.quill.collections.functional;

@FunctionalInterface
public interface Consumer3<A, B, C>
{
	public void accept(A a, B b, C c);
}
