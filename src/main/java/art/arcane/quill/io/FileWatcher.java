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

import java.io.File;

public class FileWatcher {
    protected final File file;
    private boolean exists;
    private long lastModified;
    private long size;

    public FileWatcher(File file) {
        this.file = file;
        readProperties();
    }

    protected void readProperties() {
        exists = file.exists();
        lastModified = exists ? file.lastModified() : -1;
        size = exists ? file.isDirectory() ? -2 : file.length() : -1;
    }

    public boolean checkModified() {
        long m = lastModified;
        long g = size;
        boolean mod = false;
        readProperties();

        if (lastModified != m || g != size) {
            mod = true;
        }

        return mod;
    }
}
