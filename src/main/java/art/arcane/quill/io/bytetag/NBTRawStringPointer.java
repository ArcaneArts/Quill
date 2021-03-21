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

public class NBTRawStringPointer {

    private String value;
    private int index;

    public NBTRawStringPointer(String value) {
        this.value = value;
    }

    private static boolean isSimpleChar(char c) {
        return c >= 'a' && c <= 'z'
                || c >= 'A' && c <= 'Z'
                || c >= '0' && c <= '9'
                || c == '-'
                || c == '+'
                || c == '.'
                || c == '_';
    }

    public String parseSimpleString() {
        int oldIndex = index;
        while (hasNext() && isSimpleChar(currentChar())) {
            index++;
        }
        return value.substring(oldIndex, index);
    }

    public String parseQuotedString() throws NBTRawParseException {
        int oldIndex = ++index; //ignore beginning quotes
        StringBuilder sb = null;
        boolean escape = false;
        while (hasNext()) {
            char c = next();
            if (escape) {
                if (c != '\\' && c != '"') {
                    throw parseException("invalid escape of '" + c + "'");
                }
                escape = false;
            } else {
                if (c == '\\') { //escape
                    escape = true;
                    if (sb != null) {
                        continue;
                    }
                    sb = new StringBuilder(value.substring(oldIndex, index - 1));
                    continue;
                }
                if (c == '"') {
                    return sb == null ? value.substring(oldIndex, index - 1) : sb.toString();
                }
            }
            if (sb != null) {
                sb.append(c);
            }
        }
        throw parseException("missing end quote");
    }

    public boolean nextArrayElement() {
        skipWhitespace();
        if (hasNext() && currentChar() == ',') {
            index++;
            skipWhitespace();
            return true;
        }
        return false;
    }

    public void expectChar(char c) throws NBTRawParseException {
        skipWhitespace();
        boolean hasNext = hasNext();
        if (hasNext && currentChar() == c) {
            index++;
            return;
        }
        throw parseException("expected '" + c + "' but got " + (hasNext ? "'" + currentChar() + "'" : "EOF"));
    }

    public void skipWhitespace() {
        while (hasNext() && Character.isWhitespace(currentChar())) {
            index++;
        }
    }

    public boolean hasNext() {
        return index < value.length();
    }

    public boolean hasCharsLeft(int num) {
        return this.index + num < value.length();
    }

    public char currentChar() {
        return value.charAt(index);
    }

    public char next() {
        return value.charAt(index++);
    }

    public void skip(int offset) {
        index += offset;
    }

    public char lookAhead(int offset) {
        return value.charAt(index + offset);
    }

    public NBTRawParseException parseException(String msg) {
        return new NBTRawParseException(msg, value, index);
    }
}
