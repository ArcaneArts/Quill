package art.arcane.quill.config;

import art.arcane.quill.collections.KList;

import java.io.File;

public interface ConfigWrapper
{
	public void load(File f) throws Exception;

	public void save(File f) throws Exception;

	public String save();

	public void load(String string) throws Exception;

	public void set(String key, Object o);

	public Object get(String key);

	public KList<String> keys();

	public boolean contains(String key);
}
