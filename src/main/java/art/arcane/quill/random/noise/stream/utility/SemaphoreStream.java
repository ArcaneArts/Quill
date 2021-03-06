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

package art.arcane.quill.random.noise.stream.utility;

import art.arcane.quill.random.noise.stream.BasicStream;
import art.arcane.quill.random.noise.stream.ProceduralStream;

import java.util.concurrent.Semaphore;

public class SemaphoreStream<T> extends BasicStream<T> {
    private final Semaphore semaphore;

    public SemaphoreStream(ProceduralStream<T> stream, int permits) {
        super(stream);
        this.semaphore = new Semaphore(permits);
    }

    @Override
    public double toDouble(T t) {
        return getTypedSource().toDouble(t);
    }

    @Override
    public T fromDouble(double d) {
        return getTypedSource().fromDouble(d);
    }

    @Override
    public T get(double x, double z) {
        synchronized (getTypedSource()) {
            return getTypedSource().get(x, z);
        }
    }

    @Override
    public T get(double x, double y, double z) {
        synchronized (getTypedSource()) {
            return getTypedSource().get(x, y, z);
        }
    }
}
