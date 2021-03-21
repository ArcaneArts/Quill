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

import java.io.IOException;

public class NBTRawParseException extends IOException {

    public NBTRawParseException(String msg) {
        super(msg);
    }

    public NBTRawParseException(String msg, String value, int index) {
        super(msg + " at: " + formatError(value, index));
    }

    private static String formatError(String value, int index) {
        StringBuilder builder = new StringBuilder();
        int i = Math.min(value.length(), index);
        if (i > 35) {
            builder.append("...");
        }
        builder.append(value, Math.max(0, i - 35), i);
        builder.append("<--[HERE]");
        return builder.toString();
    }
}
