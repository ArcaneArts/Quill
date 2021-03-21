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
import java.io.OutputStream;

public class MeteredOutputStream extends OutputStream {
    private OutputStream stream;
    private long writtenBytes;
    private long timeStart;

    public MeteredOutputStream(OutputStream stream) {
        this.stream = stream;
        writtenBytes = 0;
        timeStart = M.ms();
    }

    @Override
    public void write(int b) throws IOException {
        stream.write(b);
        writtenBytes++;
    }

    public void close() throws IOException {
        stream.close();
    }

    public double getBPS() {
        return ((double) writtenBytes) / (((double) getTimeElapsed()) / 1000D);
    }

    public long getTimeElapsed() {
        return M.ms() - timeStart;
    }

    public long getWrittenBytes() {
        return writtenBytes;
    }

    public long getTimeStart() {
        return timeStart;
    }
}
