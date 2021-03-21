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

package art.arcane.quill.reaction;

import art.arcane.quill.collections.KList;

public class O<T> implements Observable<T> {
    private T t = null;
    private KList<Observer<T>> observers;

    @Override
    public T get() {
        return t;
    }

    @Override
    public O<T> set(T t) {
        T x = t;
        this.t = t;

        if (observers != null && observers.hasElements()) {
            observers.forEach((o) -> o.onChanged(x, t));
        }

        return this;
    }

    @Override
    public boolean has() {
        return t != null;
    }

    @Override
    public O<T> clearObservers() {
        observers.clear();
        return this;
    }

    @Override
    public O<T> observe(Observer<T> t) {
        if (observers == null) {
            observers = new KList<>();
        }

        observers.add(t);

        return this;
    }
}
