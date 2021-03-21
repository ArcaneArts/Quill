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

package art.arcane.quill.execution.queue;

import art.arcane.quill.collections.KList;

public interface Queue<T> {
    public static <T> Queue<T> create(KList<T> t) {
        return new ShurikenQueue<T>().queue(t);
    }

    @SuppressWarnings("unchecked")
    public static <T> Queue<T> create(T... t) {
        return new ShurikenQueue<T>().queue(new KList<T>().add(t));
    }

    public Queue<T> queue(T t);

    public Queue<T> queue(KList<T> t);

    public boolean hasNext(int amt);

    public boolean hasNext();

    public T next();

    public KList<T> next(int amt);

    public Queue<T> clear();

    public int size();
}
