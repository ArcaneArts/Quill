package art.arcane.quill.random;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class RNG extends Random
{
	private static final char[] CHARGEN = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-=!@#$%^&*()_+`~[];',./<>?:\\\"{}|\\\\".toCharArray();
	private static final char[] CHARGEN_SAFE = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-".toCharArray();
	private static final long serialVersionUID = 5222938581174415179L;
	public static final RNG r = new RNG();
	private final long sx;

	public RNG()
	{
		super();
		sx = 0;
	}

	public RNG(long seed)
	{
		super(seed);
		this.sx = seed;
	}

	/**
	 * Creates a seed (long) from the hash of the seed string
	 * 
	 * @param seed
	 *            the seed (string)
	 */
	public RNG(String seed)
	{
		this(UUID.nameUUIDFromBytes(seed.getBytes(StandardCharsets.UTF_8)).getLeastSignificantBits() + UUID.nameUUIDFromBytes(seed.getBytes(StandardCharsets.UTF_8)).getMostSignificantBits() + (seed.length() * 32564));
	}

	public RNG nextParallelRNG(int signature)
	{
		return new RNG(sx + signature);
	}
	
	public <T> T pick(List<T> options)
	{
		if(options.isEmpty())
		{
			return null;
		}

		return options.get(i(0, options.size() - 1));
	}

	@Deprecated
	public RNG nextRNG()
	{
		return new RNG(nextLong());
	}

	public String s(int length)
	{
		StringBuilder sb = new StringBuilder();

		for(int i = 0; i < length; i++)
		{
			sb.append(c());
		}

		return sb.toString();
	}

	public String sSafe(int length)
	{
		StringBuilder sb = new StringBuilder();

		for(int i = 0; i < length; i++)
		{
			sb.append(cSafe());
		}

		return sb.toString();
	}

	public char c()
	{
		return CHARGEN[i(CHARGEN.length - 1)];
	}

	public char cSafe()
	{
		return CHARGEN_SAFE[i(CHARGEN_SAFE.length - 1)];
	}

	/**
	 * Pick a random enum
	 * 
	 * @param t
	 *            the enum class
	 * @return the enum
	 */
	public <T> T e(Class<T> t)
	{
		T[] c = t.getEnumConstants();
		return c[i(c.length)];
	}

	public boolean b()
	{
		return nextBoolean();
	}

	public boolean b(double percent)
	{
		return d() > percent;
	}

	public short si(int lowerBound, int upperBound)
	{
		if(lowerBound == upperBound)
		{
			return (short) lowerBound;
		}

		return (short) (lowerBound + (nextFloat() * ((upperBound - lowerBound) + 1)));
	}

	public short si(int upperBound)
	{
		return si(0, upperBound);
	}

	public short si()
	{
		return si(1);
	}

	public float f(float lowerBound, float upperBound)
	{
		if(lowerBound == upperBound)
		{
			return lowerBound;
		}

		return lowerBound + (nextFloat() * ((upperBound - lowerBound)));
	}

	public float f(float upperBound)
	{
		return f(0, upperBound);
	}

	public float f()
	{
		return f(1);
	}

	public double d(double lowerBound, double upperBound)
	{
		if(lowerBound == upperBound)
		{
			return lowerBound;
		}

		return lowerBound + (nextDouble() * ((upperBound - lowerBound)));
	}

	public double d(double upperBound)
	{
		return d(0, upperBound);
	}

	public double d()
	{
		return d(1);
	}

	/**
	 * Pick a random value (upper inclusive) (lower inclusive)
	 * 
	 * @param lowerBound
	 *            the upper inclusive bound
	 * @param upperBound
	 *            the lower inclusive bound
	 * @return a value at or between lower and upper bounds
	 */
	public int i(int lowerBound, int upperBound)
	{
		if(lowerBound == upperBound)
		{
			return lowerBound;
		}

		return (int) Math.round(d(lowerBound, upperBound));
	}

	public int i(int upperBound)
	{
		return i(0, upperBound);
	}

	public long l(long lowerBound, long upperBound)
	{
		return Math.round(d(lowerBound, upperBound));
	}

	public long l(long upperBound)
	{
		return l(0, upperBound);
	}

	public int imax()
	{
		return i(Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	public long lmax()
	{
		return l(Long.MIN_VALUE, Long.MAX_VALUE);
	}

	public float fmax()
	{
		return f(Float.MIN_VALUE, Float.MAX_VALUE);
	}

	public double dmax()
	{
		return d(Double.MIN_VALUE, Double.MAX_VALUE);
	}

	public short simax()
	{
		return si(Short.MIN_VALUE, Short.MAX_VALUE);
	}
}
