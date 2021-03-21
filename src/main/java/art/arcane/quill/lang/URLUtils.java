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

package art.arcane.quill.lang;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class URLUtils {
    public static int getFileSize(URL url) {
        URLConnection conn = null;
        try {
            conn = url.openConnection();

            if (conn instanceof HttpURLConnection) {
                ((HttpURLConnection) conn).setRequestMethod("HEAD");
            }

            conn.getInputStream();

            return conn.getContentLength();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn instanceof HttpURLConnection) {
                ((HttpURLConnection) conn).disconnect();
            }
        }
    }
}
