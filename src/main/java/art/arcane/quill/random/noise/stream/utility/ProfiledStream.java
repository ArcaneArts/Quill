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

import art.arcane.quill.collections.KList;
import art.arcane.quill.format.Form;
import art.arcane.quill.math.PSW;
import art.arcane.quill.math.RollingSequence;
import art.arcane.quill.random.noise.stream.BasicStream;
import art.arcane.quill.random.noise.stream.ProceduralStream;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class ProfiledStream<T> extends BasicStream<T> {
    public static final AtomicInteger ids = new AtomicInteger();
    private final int id;
    private final RollingSequence metrics;

    public ProfiledStream(ProceduralStream<T> stream, int memory) {
        super(stream);
        this.metrics = new RollingSequence(memory);
        this.id = ids.getAndAdd(1);
    }

    public static void print(Consumer<String> printer, ProceduralStream<?> stream) {
        KList<ProfiledTail> tails = getTails(stream);
        int ind = tails.size();
        for (ProfiledTail i : tails) {
            printer.accept(Form.repeat("  ", ind) + i);
            ind--;
        }
    }

    private static KList<ProceduralStream<?>> getAllChildren(ProceduralStream<?> s) {
        KList<ProceduralStream<?>> v = new KList<>();
        ProceduralStream<?> cursor = s;

        for (int i = 0; i < 32; i++) {
            v.add(cursor);
            cursor = nextChuld(cursor);

            if (cursor == null) {
                break;
            }
        }

        return v;
    }

    private static ProceduralStream<?> nextChuld(ProceduralStream<?> s) {
        ProceduralStream<?> v = s.getTypedSource();
        return v == null ? s.getSource() : v;
    }

    private static ProfiledTail getTail(ProceduralStream<?> t) {
        if (t instanceof ProfiledStream) {
            ProfiledStream<?> s = ((ProfiledStream<?>) t);

            return new ProfiledTail(s.getId(), s.getMetrics(), s.getClass().getSimpleName().replaceAll("\\QStream\\E", ""));
        }

        return null;
    }

    private static KList<ProfiledTail> getTails(ProceduralStream<?> t) {
        KList<ProfiledTail> tails = new KList<>();

        for (ProceduralStream<?> v : getAllChildren(t)) {
            ProfiledTail p = getTail(v);

            if (p != null) {
                tails.add(p);
            }
        }

        if (tails.isEmpty()) {
            return null;
        }

        ProfiledTail cursor = tails.popLast();
        KList<ProfiledTail> tailx = new KList<>();
        tailx.add(cursor);

        while (tails.isNotEmpty()) {
            tailx.add(cursor);
            ProfiledTail parent = tails.popLast();
            parent.setChild(cursor);
            cursor = parent;
            tailx.add(cursor);
        }

        return tailx;
    }

    public int getId() {
        return id;
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
        PSW p = PSW.start();
        T t = getTypedSource().get(x, z);
        try {
            metrics.put(p.getMilliseconds());
        } catch (Throwable e) {
        }

        return t;
    }

    @Override
    public T get(double x, double y, double z) {
        PSW p = PSW.start();
        T t = getTypedSource().get(x, y, z);
        try {
            metrics.put(p.getMilliseconds());
        } catch (Throwable e) {
        }

        return t;
    }

    public RollingSequence getMetrics() {
        return metrics;
    }

    @Data
    private static class ProfiledTail {
        private final int id;
        private final RollingSequence metrics;
        private final String name;
        private ProfiledTail child;

        public ProfiledTail(int id, RollingSequence metrics, String name) {
            this.id = id;
            this.metrics = metrics;
            this.name = name;
        }

        public String toString() {
            return id + "-" + name + ": " + Form.duration(metrics.getAverage(), 2);
        }
    }
}
