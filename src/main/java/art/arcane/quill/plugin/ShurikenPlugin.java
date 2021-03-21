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

import art.arcane.quill.execution.queue.QueueExecutor;
import art.arcane.quill.logging.L;

public abstract class ShurikenPlugin extends L implements Plugin {
    private boolean enabled;
    private PluginManager manager;
    private QueueExecutor executor;

    public ShurikenPlugin() {

    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setManager(PluginManager manager) {
        this.manager = manager;
    }

    @Override
    public void enable() {
        if (isEnabled()) {
            return;
        }

        executor = new QueueExecutor();
        executor.setName(getPluginManager().getConfig().getName());
        executor.start();
        enabled = true;
        run(this::onEnable);
        run(() -> info(getPluginManager().getConfig().getName() + " Enabled"));
    }

    public void run(Runnable r) {
        executor.queue().queue(r);
    }

    @Override
    public void disable() {
        if (!isEnabled()) {
            return;
        }

        enabled = false;
        run(this::onDisable);
        run(() -> info(getPluginManager().getConfig().getName() + " Disabled"));
        executor.shutdown();

        try {
            executor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PluginManager getPluginManager() {
        return manager;
    }
}
