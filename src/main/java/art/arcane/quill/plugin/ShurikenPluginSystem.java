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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ShurikenPluginSystem implements PluginSystem {
    private KList<PluginManager> managers;

    public ShurikenPluginSystem() {
        managers = new KList<PluginManager>();
    }

    @Override
    public KList<PluginManager> getPlugins() {
        return managers.copy();
    }

    @Override
    public PluginManager sideload(String pluginName, String classname) throws PluginException {
        try {
            PluginManager pm = new SideloadedPluginManager(pluginName, classname);
            managers.add(pm);
            return pm;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException | IOException e) {
            throw new PluginException(e);
        }
    }

    @Override
    public PluginManager load(File p) throws PluginException {
        try {
            PluginManager pm = new JarPluginManager(p);
            managers.add(pm);
            return pm;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException | IOException e) {
            throw new PluginException(e);
        }
    }

    @Override
    public void loadAll(File folder) throws PluginException {
        for (File i : folder.listFiles()) {
            if (i.isFile() && i.getName().endsWith(".jar")) {
                load(i);
            }
        }
    }

    @Override
    public void disableAll() {
        for (PluginManager i : getPlugins()) {
            i.getPlugin().disable();
        }
    }

    @Override
    public void unloadAll() {
        disableAll();

        for (PluginManager i : getPlugins()) {
            i.unload();
        }

        managers.clear();
    }

    @Override
    public PluginManager getPlugin(String name) {
        for (PluginManager i : getPlugins()) {
            if (i.getConfig().getName().equals(name)) {
                return i;
            }
        }

        return null;
    }

    @Override
    public void enableAll() {
        for (PluginManager i : getPlugins()) {
            i.getPlugin().enable();
        }
    }
}
