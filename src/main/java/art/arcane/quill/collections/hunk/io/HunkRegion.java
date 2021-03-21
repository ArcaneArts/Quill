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

package art.arcane.quill.collections.hunk.io;

import art.arcane.quill.collections.KMap;
import art.arcane.quill.io.bytetag.jnbt.CompoundTag;
import art.arcane.quill.io.bytetag.jnbt.NBTInputStream;
import art.arcane.quill.io.bytetag.jnbt.NBTOutputStream;
import art.arcane.quill.io.bytetag.jnbt.Tag;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class HunkRegion
{
	private final File folder;
	private CompoundTag compound;
	private final int x;
	private final int z;

	public HunkRegion(File folder, int x, int z, CompoundTag compound)
	{
		this.compound = fix(compound);
		this.folder = folder;
		this.x = x;
		this.z = z;
		folder.mkdirs();
	}

	public HunkRegion(File folder, int x, int z)
	{
		this(folder, x, z, new CompoundTag(x + "." + z, new KMap<>()));
		File f = getFile();

		if(f.exists())
		{
			try
			{
				NBTInputStream in = new NBTInputStream(new FileInputStream(f));
				compound = fix((CompoundTag) in.readTag());
				in.close();
			}

			catch(Throwable ignored)
			{

			}
		}
	}

	public CompoundTag getCompound() {
		return compound;
	}

	private CompoundTag fix(CompoundTag readTag)
	{
		Map<String, Tag> v = readTag.getValue();

		if(!(v instanceof KMap))
		{
			return new CompoundTag(readTag.getName(), new KMap<String, Tag>(v));
		}

		return readTag;
	}

	public File getFile()
	{
		return new File(folder, x + "." + z + ".dat");
	}

	public void save() throws IOException
	{
		synchronized(compound)
		{
			File f = getFile();
			FileOutputStream fos = new FileOutputStream(f);
			NBTOutputStream out = new NBTOutputStream(fos);
			out.writeTag(compound);
			out.close();
		}
	}

	public int getX() {
		return x;
	}
	public int getZ() {
		return z;
	}

}
