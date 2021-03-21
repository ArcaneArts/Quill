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

import java.util.List;

/**
 * Works like an iterator except it loops back to the start when the last entry is hit. Round robins do not store their own data which means they access the data source which may or may not change. Changes in the parent source are reflected here.
 * @param <T> the type
 */
public interface RoundRobin<T> {
    public boolean hasNext();

    public boolean isEmpty();

    /**
     * Gets the next element in the order
     * @return the next element, or null if the data source is empty
     */
    public T next();

    public KList<T> list();
}
