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

import art.arcane.quill.collections.KList;
import art.arcane.quill.collections.KMap;

import java.io.File;

public class FolderWatcher extends FileWatcher {
    private KMap<File, FolderWatcher> watchers;
    private KList<File> changed;
    private KList<File> created;
    private KList<File> deleted;

    public FolderWatcher(File file) {
        super(file);
    }

    protected void readProperties() {
        if (watchers == null) {
            watchers = new KMap<>();
            changed = new KList<>();
            created = new KList<>();
            deleted = new KList<>();
        }

        if (file.isDirectory()) {
            for (File i : file.listFiles()) {
                if (!watchers.containsKey(i)) {
                    watchers.put(i, new FolderWatcher(i));
                }
            }

            if (watchers == null) {
                System.out.print("wtf");
            }

            for (File i : watchers.k()) {
                if (!i.exists()) {
                    watchers.remove(i);
                }
            }
        } else {
            super.readProperties();
        }
    }

    public boolean checkModified() {
        changed.clear();
        created.clear();
        deleted.clear();

        if (file.isDirectory()) {
            KMap<File, FolderWatcher> w = watchers.copy();
            readProperties();

            for (File i : w.k()) {
                if (!watchers.containsKey(i)) {
                    deleted.add(i);
                }
            }

            for (File i : watchers.k()) {
                if (!w.containsKey(i)) {
                    created.add(i);
                } else {
                    FolderWatcher fw = watchers.get(i);
                    if (fw.checkModified()) {
                        changed.add(fw.file);
                    }

                    changed.addAll(fw.getChanged());
                    created.addAll(fw.getCreated());
                    deleted.addAll(fw.getDeleted());
                }
            }

            return !changed.isEmpty() || !created.isEmpty() || !deleted.isEmpty();
        }

        return super.checkModified();
    }

    public KMap<File, FolderWatcher> getWatchers() {
        return watchers;
    }

    public KList<File> getChanged() {
        return changed;
    }

    public KList<File> getCreated() {
        return created;
    }

    public KList<File> getDeleted() {
        return deleted;
    }
}
