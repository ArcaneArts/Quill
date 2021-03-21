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

package art.arcane.quill.random.noise.stream.convert;

import art.arcane.quill.collections.KMap;
import art.arcane.quill.random.noise.stream.BasicLayer;
import art.arcane.quill.random.noise.stream.ProceduralStream;

import java.util.function.Function;


public class CachedConversionStream<T, V> extends BasicLayer implements ProceduralStream<V> {
    private final ProceduralStream<T> stream;
    private final Function<T, V> converter;
    private final KMap<T, V> cache;

    public CachedConversionStream(ProceduralStream<T> stream, Function<T, V> converter) {
        super();
        this.stream = stream;
        this.converter = converter;
        cache = new KMap<>();
    }

    @Override
    public double toDouble(V t) {
        return 0;
    }

    @Override
    public V fromDouble(double d) {
        return null;
    }

    @Override
    public ProceduralStream<V> getTypedSource() {
        return null;
    }

    @Override
    public ProceduralStream<?> getSource() {
        return stream;
    }

    @Override
    public V get(double x, double z) {
        return cache.compute(stream.get(x, z), (k, v) -> v != null ? v : converter.apply(k));
    }

    @Override
    public V get(double x, double y, double z) {
        return cache.compute(stream.get(x, y, z), (k, v) -> v != null ? v : converter.apply(k));
    }
}
