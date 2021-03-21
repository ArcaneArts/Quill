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

package art.arcane.quill.io;

import art.arcane.quill.math.M;

import java.io.IOException;
import java.io.InputStream;

public class MeteredInputStream extends InputStream {
    private InputStream stream;
    private long readBytes;
    private long timeStart;

    public MeteredInputStream(InputStream stream) {
        this.stream = stream;
        readBytes = 0;
        timeStart = M.ms();
    }

    @Override
    public int read() throws IOException {
        readBytes++;
        return stream.read();
    }

    public void close() throws IOException {
        stream.close();
    }

    public double getBPS() {
        return ((double) readBytes) / (((double) getTimeElapsed()) / 1000D);
    }

    public long getTimeElapsed() {
        return M.ms() - timeStart;
    }

    public long getReadBytes() {
        return readBytes;
    }

    public long getTimeStart() {
        return timeStart;
    }
}
