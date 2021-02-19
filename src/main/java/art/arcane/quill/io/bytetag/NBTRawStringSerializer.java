package art.arcane.quill.io.bytetag;

import java.io.*;

public interface NBTRawStringSerializer<T> extends NBTRawSerializer<T> {

	void toWriter(T object, Writer writer) throws IOException;

	default String toString(T object) throws IOException {
		Writer writer = new StringWriter();
		toWriter(object, writer);
		writer.flush();
		return writer.toString();
	}

	@Override
	default void toStream(T object, OutputStream stream) throws IOException {
		Writer writer = new OutputStreamWriter(stream);
		toWriter(object, writer);
		writer.flush();
	}

	@Override
	default void toFile(T object, File file) throws IOException {
		try (Writer writer = new FileWriter(file)) {
			toWriter(object, writer);
		}
	}
}
