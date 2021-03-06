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

/*
 * Copyright (c) 2005, Joe Desbonnet, (jdesbonnet@gmail.com)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY <copyright holder> ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <copyright holder> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package art.arcane.quill.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Joe Desbonnet, jdesbonnet@gmail.com, cyberpwn
 */
public class BinaryTools {
    public static void patch(File oldIn, File diffIn, File newOut) throws IOException {
        BPatch.bspatch(oldIn, newOut, diffIn);
    }

    public static void diff(File oldIn, File newIn, File diffOut) throws IOException {
        BDiff.bsdiff(oldIn, newIn, diffOut);
    }

    /**
     * Equiv of C library memcmp().
     *
     * @param s1
     * @param s1offset
     * @param s2
     * @param n
     * @return
     */
    /*
     * public final static int memcmp(byte[] s1, int s1offset, byte[] s2, int
     * s2offset, int n) {
     *
     * if ((s1offset + n) > s1.length) { n = s1.length - s1offset; } if ((s2offset +
     * n) > s2.length) { n = s2.length - s2offset; } for (int i = 0; i < n; i++) {
     * if (s1[i + s1offset] != s2[i + s2offset]) { return s1[i + s1offset] < s2[i +
     * s2offset] ? -1 : 1; } }
     *
     * return 0; }
     */

    /**
     * Equiv of C library memcmp().
     *
     * @param s1
     * @param s1offset
     * @param s2
     * @return
     */
    public static int memcmp(byte[] s1, int s1offset, byte[] s2, int s2offset) {
        int n = s1.length - s1offset;

        if (n > (s2.length - s2offset)) {
            n = s2.length - s2offset;
        }
        for (int i = 0; i < n; i++) {
            if (s1[i + s1offset] != s2[i + s2offset]) {
                return s1[i + s1offset] < s2[i + s2offset] ? -1 : 1;
            }
        }

        return 0;
    }

    public static boolean readFromStream(InputStream in, byte[] buf, int offset, int len) throws IOException {
        int totalBytesRead = 0;
        int nbytes;

        while (totalBytesRead < len) {
            nbytes = in.read(buf, offset + totalBytesRead, len - totalBytesRead);
            if (nbytes < 0) {
                System.err.println("readFromStream(): returning prematurely. Read " + totalBytesRead + " bytes");
                return false;
            }
            totalBytesRead += nbytes;
        }

        return true;
    }
}