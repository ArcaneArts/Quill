package art.arcane.quill.collections;

public class Shrinkwrap<T>
{
	private T t;

	public Shrinkwrap(T t)
	{
		set(t);
	}

	public Shrinkwrap()
	{
		this(null);
	}

	public T get()
	{
		return t;
	}

	public void set(T t)
	{
		this.t = t;
	}
}
