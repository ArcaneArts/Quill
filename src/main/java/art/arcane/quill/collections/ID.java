package art.arcane.quill.collections;

import art.arcane.quill.random.RNG;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ID {
    public static final int LENGTH = 64;
    private String id; // Can't be final since it's used in really dirty ways.

    private ID(String id)
    {
        this.id = id;
    }

    public static ID fromString(String v)
    {
        return new ID(v);
    }

    public static ID fromHash(String v)
    {
        return new ID(new RNG(v.hashCode()).sSafe(LENGTH));
    }

    public ID()
    {
        this(new RNG().sSafe(LENGTH));
    }

    public String toString()
    {
        return id;
    }
}
