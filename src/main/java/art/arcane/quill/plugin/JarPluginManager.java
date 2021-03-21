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

import art.arcane.quill.io.IO;
import art.arcane.quill.logging.L;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.zip.ZipException;

public class JarPluginManager implements PluginManager {

    private PluginClassLoader classLoader;
    private File jar;
    private PluginConfig config;
    private Plugin plugin;

    public JarPluginManager(File jar) throws ZipException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        IO.readEntry(jar, "plugin.json", (in) -> {
            try {
                config = new Gson().fromJson(IO.readAll(in), PluginConfig.class);
            } catch (JsonSyntaxException | IOException e) {
                e.printStackTrace();
            }
        });
        this.jar = jar;
        this.classLoader = new PluginClassLoader(new URL[]{jar.toURI().toURL()}, getClassLoader());
        Class<?> pclass = classLoader.loadClass(config.getMain());
        plugin = (Plugin) pclass.getConstructor().newInstance();
        plugin.setManager(this);
    }

    @Override
    public PluginClassLoader getClassLoader() {
        return classLoader;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public File getJar() {
        return jar;
    }

    @Override
    public void unload() {
        if (plugin.isEnabled()) {
            plugin.disable();
        }

        plugin.setManager(null);
        plugin = null;
        try {
            classLoader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        classLoader = null;
        L.i("Unloaded " + config.getName());
    }

    @Override
    public PluginConfig getConfig() {
        return config;
    }
}
