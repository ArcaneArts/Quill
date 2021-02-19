package art.arcane.quill.plugin;

import art.arcane.quill.collections.KList;

import java.io.File;

public interface PluginSystem
{
	public KList<PluginManager> getPlugins();
	
	public PluginManager load(File p) throws PluginException;
	
	public void loadAll(File folder) throws PluginException;
	
	public void disableAll();
	
	public void enableAll();
	
	public void unloadAll();
	
	public PluginManager getPlugin(String name);
	
	public PluginManager sideload(String pluginName, String classname) throws PluginException;
}
