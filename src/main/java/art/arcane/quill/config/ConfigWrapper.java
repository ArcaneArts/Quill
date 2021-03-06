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

package art.arcane.quill.config;

import art.arcane.quill.collections.KList;

import java.io.File;

public interface ConfigWrapper {
    public void load(File f) throws Exception;

    public void save(File f) throws Exception;

    public String save();

    public void load(String string) throws Exception;

    public void set(String key, Object o);

    public Object get(String key);

    public KList<String> keys();

    public boolean contains(String key);
}
