package art.arcane.quill.io.bytetag.mca;

@FunctionalInterface
public interface MCAExceptionFunction<T, R, E extends Exception> {

	R accept(T t) throws E;
}
