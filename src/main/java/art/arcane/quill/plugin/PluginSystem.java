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

package art.arcane.quill.plugin;

import art.arcane.quill.collections.KList;

import java.io.File;

public interface PluginSystem {
    public KList<PluginManager> getPlugins();

    public PluginManager load(File p) throws PluginException;

    public void loadAll(File folder) throws PluginException;

    public void disableAll();

    public void enableAll();

    public void unloadAll();

    public PluginManager getPlugin(String name);

    public PluginManager sideload(String pluginName, String classname) throws PluginException;
}
