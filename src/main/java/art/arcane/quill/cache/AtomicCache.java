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

package art.arcane.quill.cache;

import art.arcane.quill.math.M;

import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

public class AtomicCache<T> {
    private final ReentrantLock check;
    private final ReentrantLock time;
    private final ReentrantLock write;
    private final boolean nullSupport;
    private transient volatile T t;
    private transient volatile long a;
    private transient volatile int validations;

    public AtomicCache() {
        this(false);
    }

    public AtomicCache(boolean nullSupport) {
        this.nullSupport = nullSupport;
        check = new ReentrantLock();
        write = new ReentrantLock();
        time = new ReentrantLock();
        validations = 0;
        a = -1;
        t = null;
    }

    public void reset() {
        check.lock();
        write.lock();
        time.lock();
        a = -1;
        t = null;
        time.unlock();
        write.unlock();
        check.unlock();
    }

    public T aquire(Supplier<T> t) {
        if (nullSupport) {
            return aquireNull(t);
        }

        if (this.t != null && validations > 1000) {
            return this.t;
        }

        if (this.t != null && M.ms() - a > 1000) {
            if (this.t != null) {
                validations++;
            }

            return this.t;
        }

        check.lock();

        if (this.t == null) {
            write.lock();
            this.t = t.get();

            time.lock();

            if (a == -1) {
                a = M.ms();
            }

            time.unlock();
            write.unlock();
        }

        check.unlock();
        return this.t;
    }

    public T aquireNull(Supplier<T> t) {
        if (validations > 1000) {
            return this.t;
        }

        if (M.ms() - a > 1000) {
            validations++;
            return this.t;
        }

        check.lock();
        write.lock();
        this.t = t.get();

        time.lock();

        if (a == -1) {
            a = M.ms();
        }

        time.unlock();
        write.unlock();
        check.unlock();
        return this.t;
    }
}
