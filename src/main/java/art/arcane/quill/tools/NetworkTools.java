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

package art.arcane.quill.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NetworkTools {
    public static String ip = "?";

    public static String getPublicIP() {
        if (!ip.equals("?")) {
            return ip;
        }

        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            ip = in.readLine();
            in.close();
            return ip;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static long getContentLength(String urx) {
        try {
            URL url = new URL(urx);
            URLConnection conn = null;

            try {
                conn = url.openConnection();

                if (conn instanceof HttpURLConnection) {
                    ((HttpURLConnection) conn).setRequestMethod("HEAD");
                }

                conn.getInputStream();
                return conn.getContentLength();
            } catch (IOException e) {
                return -1;
            } finally {
                if (conn instanceof HttpURLConnection) {
                    ((HttpURLConnection) conn).disconnect();
                }
            }
        } catch (MalformedURLException e1) {
            return -1;
        }
    }
}
