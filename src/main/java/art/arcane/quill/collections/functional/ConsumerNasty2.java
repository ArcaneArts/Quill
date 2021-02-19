package art.arcane.quill.collections.functional;

@FunctionalInterface
public interface ConsumerNasty2<A, B>
{
	public void accept(A a, B b) throws Throwable;
}
