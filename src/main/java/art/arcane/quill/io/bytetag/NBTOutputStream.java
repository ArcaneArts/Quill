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

package art.arcane.quill.io.bytetag;

import art.arcane.quill.io.bytetag.tag.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class NBTOutputStream extends DataOutputStream implements NBTRawMaxDepthIO {

    private static Map<Byte, NBTRawExceptionTriConsumer<NBTOutputStream, NBTTag<?>, Integer, IOException>> writers = new HashMap<>();
    private static Map<Class<?>, Byte> classIdMapping = new HashMap<>();

    static {
        put(NBTEndTag.ID, (o, t, d) -> {
        }, NBTEndTag.class);
        put(NBTByteTag.ID, (o, t, d) -> writeByte(o, t), NBTByteTag.class);
        put(NBTShortTag.ID, (o, t, d) -> writeShort(o, t), NBTShortTag.class);
        put(NBTIntTag.ID, (o, t, d) -> writeInt(o, t), NBTIntTag.class);
        put(NBTLongTag.ID, (o, t, d) -> writeLong(o, t), NBTLongTag.class);
        put(NBTFloatTag.ID, (o, t, d) -> writeFloat(o, t), NBTFloatTag.class);
        put(NBTDoubleTag.ID, (o, t, d) -> writeDouble(o, t), NBTDoubleTag.class);
        put(NBTByteArrayTag.ID, (o, t, d) -> writeByteArray(o, t), NBTByteArrayTag.class);
        put(NBTStringTag.ID, (o, t, d) -> writeString(o, t), NBTStringTag.class);
        put(NBTListTag.ID, NBTOutputStream::writeList, NBTListTag.class);
        put(NBTCompoundTag.ID, NBTOutputStream::writeCompound, NBTCompoundTag.class);
        put(NBTIntArrayTag.ID, (o, t, d) -> writeIntArray(o, t), NBTIntArrayTag.class);
        put(NBTLongArrayTag.ID, (o, t, d) -> writeLongArray(o, t), NBTLongArrayTag.class);
    }

    public NBTOutputStream(OutputStream out) {
        super(out);
    }

    private static void put(byte id, NBTRawExceptionTriConsumer<NBTOutputStream, NBTTag<?>, Integer, IOException> f, Class<?> clazz) {
        writers.put(id, f);
        classIdMapping.put(clazz, id);
    }

    static byte idFromClass(Class<?> clazz) {
        Byte id = classIdMapping.get(clazz);
        if (id == null) {
            throw new IllegalArgumentException("unknown Tag class " + clazz.getName());
        }
        return id;
    }

    private static void writeByte(NBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeByte(((NBTByteTag) tag).asByte());
    }

    private static void writeShort(NBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeShort(((NBTShortTag) tag).asShort());
    }

    private static void writeInt(NBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeInt(((NBTIntTag) tag).asInt());
    }

    private static void writeLong(NBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeLong(((NBTLongTag) tag).asLong());
    }

    private static void writeFloat(NBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeFloat(((NBTFloatTag) tag).asFloat());
    }

    private static void writeDouble(NBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeDouble(((NBTDoubleTag) tag).asDouble());
    }

    private static void writeString(NBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeUTF(((NBTStringTag) tag).getValue());
    }

    private static void writeByteArray(NBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeInt(((NBTByteArrayTag) tag).length());
        out.write(((NBTByteArrayTag) tag).getValue());
    }

    private static void writeIntArray(NBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeInt(((NBTIntArrayTag) tag).length());
        for (int i : ((NBTIntArrayTag) tag).getValue()) {
            out.writeInt(i);
        }
    }

    private static void writeLongArray(NBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeInt(((NBTLongArrayTag) tag).length());
        for (long l : ((NBTLongArrayTag) tag).getValue()) {
            out.writeLong(l);
        }
    }

    private static void writeList(NBTOutputStream out, NBTTag<?> tag, int maxDepth) throws IOException {
        out.writeByte(idFromClass(((NBTListTag<?>) tag).getTypeClass()));
        out.writeInt(((NBTListTag<?>) tag).size());
        for (NBTTag<?> t : ((NBTListTag<?>) tag)) {
            out.writeRawTag(t, out.decrementMaxDepth(maxDepth));
        }
    }

    private static void writeCompound(NBTOutputStream out, NBTTag<?> tag, int maxDepth) throws IOException {
        for (Map.Entry<String, NBTTag<?>> entry : (NBTCompoundTag) tag) {
            if (entry.getValue().getID() == 0) {
                throw new IOException("end tag not allowed");
            }
            out.writeByte(entry.getValue().getID());
            out.writeUTF(entry.getKey());
            out.writeRawTag(entry.getValue(), out.decrementMaxDepth(maxDepth));
        }
        out.writeByte(0);
    }

    public void writeTag(NBTRawNamedTag tag, int maxDepth) throws IOException {
        writeByte(tag.getTag().getID());
        if (tag.getTag().getID() != 0) {
            writeUTF(tag.getName() == null ? "" : tag.getName());
        }
        writeRawTag(tag.getTag(), maxDepth);
    }

    public void writeTag(NBTTag<?> tag, int maxDepth) throws IOException {
        writeByte(tag.getID());
        if (tag.getID() != 0) {
            writeUTF("");
        }
        writeRawTag(tag, maxDepth);
    }

    public void writeRawTag(NBTTag<?> tag, int maxDepth) throws IOException {
        NBTRawExceptionTriConsumer<NBTOutputStream, NBTTag<?>, Integer, IOException> f;
        if ((f = writers.get(tag.getID())) == null) {
            throw new IOException("invalid tag \"" + tag.getID() + "\"");
        }
        f.accept(this, tag, maxDepth);
    }
}
