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

public class ShurikenQueue<T> implements Queue<T> {
    private KList<T> queue;
    private boolean randomPop;
    private boolean reversePop;

    public ShurikenQueue() {
        clear();
    }

    public ShurikenQueue<T> responsiveMode() {
        reversePop = true;
        return this;
    }

    public ShurikenQueue<T> randomMode() {
        randomPop = true;
        return this;
    }

    @Override
    public ShurikenQueue<T> queue(T t) {
        queue.add(t);
        return this;
    }

    @Override
    public ShurikenQueue<T> queue(KList<T> t) {
        queue.add(t);
        return this;
    }

    @Override
    public boolean hasNext(int amt) {
        return queue.size() >= amt;
    }

    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    @Override
    public T next() {
        return reversePop ? queue.popLast() : randomPop ? queue.popRandom() : queue.pop();
    }

    @Override
    public KList<T> next(int amt) {
        KList<T> t = new KList<>();

        for (int i = 0; i < amt; i++) {
            if (!hasNext()) {
                break;
            }

            t.add(next());
        }

        return t;
    }

    @Override
    public ShurikenQueue<T> clear() {
        queue = new KList<T>();
        return this;
    }

    @Override
    public int size() {
        return queue.size();
    }
}
