package art.arcane.quill.io.bytetag;

@FunctionalInterface
public interface NBTRawExceptionTriConsumer<T, U, V, E extends Exception> {

	void accept(T t, U u, V v) throws E;
}
