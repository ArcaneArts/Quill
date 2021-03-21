/*
 * This file is part of Quill by Arcane Arts.
 *
 * Quill by Arcane Arts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * Quill by Arcane Arts is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License in this package for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Quill.  If not, see <https://www.gnu.org/licenses/>.
 */

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
