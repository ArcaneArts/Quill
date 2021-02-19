package art.arcane.quill.collections;

/**
 * Represents a number that can be finalized and be changed
 *
 * @author cyberpwn
 */
public class FinalDouble extends Wrapper<Double>
{
	public FinalDouble(Double t)
	{
		super(t);
	}

	/**
	 * Add to this value
	 *
	 * @param i
	 *            the number to add to this value (value = value + i)
	 */
	public void add(double i)
	{
		set(get() + i);
	}

	/**
	 * Subtract from this value
	 *
	 * @param i
	 *            the number to subtract from this value (value = value - i)
	 */
	public void sub(double i)
	{
		set(get() - i);
	}
}
