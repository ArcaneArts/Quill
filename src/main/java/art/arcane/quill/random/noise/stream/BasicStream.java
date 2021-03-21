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

package art.arcane.quill.random.noise.stream;

public abstract class BasicStream<T> extends BasicLayer implements ProceduralStream<T> {
    private final ProceduralStream<T> source;

    public BasicStream(ProceduralStream<T> source) {
        super();
        this.source = source;
    }

    public BasicStream() {
        this(null);
    }


    @Override
    public ProceduralStream<T> getTypedSource() {
        return source;
    }

    @Override
    public ProceduralStream<?> getSource() {
        return getTypedSource();
    }

    @Override
    public abstract T get(double x, double z);

    @Override
    public abstract T get(double x, double y, double z);

    @Override
    public abstract double toDouble(T t);

    @Override
    public abstract T fromDouble(double d);
}
