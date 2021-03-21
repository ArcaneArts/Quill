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

import java.util.concurrent.atomic.AtomicInteger;

public class KListRoundRobin<T> implements RoundRobin<T> {
    private final AtomicInteger cursor;
    private final KList<T> data;

    public KListRoundRobin(KList<T> data)
    {
        this.data = data;
        this.cursor = new AtomicInteger(0);
    }

    @Override
    public boolean hasNext() {
        return data.isNotEmpty();
    }

    @Override
    public boolean isEmpty() {
        return !hasNext();
    }

    @Override
    public T next() {
        if(isEmpty())
        {
            return null;
        }

        int index = cursor.getAndIncrement();

        if(!data.hasIndex(index))
        {
            cursor.set(0);
            index = 0;
        }

        return data.getOrNull(index);
    }

    @Override
    public KList<T> list() {
        return data;
    }
}
