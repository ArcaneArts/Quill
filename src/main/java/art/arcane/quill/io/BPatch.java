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

import java.io.*;
import java.util.zip.GZIPInputStream;

/**
 * Java Binary patcher (based on bspatch by Colin Percival)
 *
 * @author Joe Desbonnet, jdesbonnet@gmail.com
 */
public class BPatch {
    @SuppressWarnings("resource")
    public static void bspatch(File oldFile, File newFile, File diffFile) throws IOException {
        int oldpos, newpos;
        DataInputStream diffIn = new DataInputStream(new FileInputStream(diffFile));

        // Diff file header. Comprises 4 x 64 bit fields:
        // 0 8 16 24 32 (byte offset)
        // +---------------+---------------+---------------+---------------+
        // | headerMagic | ctrlBlockLen | diffBlockLen | newsize |
        // +---------------+---------------+---------------+---------------+
        // headerMagic: Always "jbdiff40" (8 bytes)
        // ctrlBlockLen: Length of gzip compressed ctrlBlock (64 bit long)
        // diffBlockLen: length of gzip compressed diffBlock (64 bit long)
        // newsize: size of new file in bytes (64 bit long)

        diffIn.readLong();
        long ctrlBlockLen = diffIn.readLong();
        long diffBlockLen = diffIn.readLong();
        int newsize = (int) diffIn.readLong();

        FileInputStream in;
        in = new FileInputStream(diffFile);
        in.skip(ctrlBlockLen + 32);
        GZIPInputStream diffBlockIn = new GZIPInputStream(in);
        in = new FileInputStream(diffFile);
        in.skip(diffBlockLen + ctrlBlockLen + 32);
        GZIPInputStream extraBlockIn = new GZIPInputStream(in);

        /*
         * Read in old file (file to be patched) to oldBuf
         */
        int oldsize = (int) oldFile.length();
        byte[] oldBuf = new byte[oldsize + 1];
        FileInputStream oldIn = new FileInputStream(oldFile);
        BinaryTools.readFromStream(oldIn, oldBuf, 0, oldsize);
        oldIn.close();

        byte[] newBuf = new byte[newsize + 1];

        oldpos = 0;
        newpos = 0;
        int[] ctrl = new int[3];
        while (newpos < newsize) {
            for (int i = 0; i <= 2; i++) {
                ctrl[i] = diffIn.readInt();
                // System.err.println (" ctrl[" + i + "]=" + ctrl[i]);
            }

            if (newpos + ctrl[0] > newsize) {
                System.err.println("Corrupt patch\n");
                return;
            }

            /*
             * Read ctrl[0] bytes from diffBlock stream
             */

            if (!BinaryTools.readFromStream(diffBlockIn, newBuf, newpos, ctrl[0])) {
                System.err.println("error reading from extraIn");
                return;
            }

            for (int i = 0; i < ctrl[0]; i++) {
                if ((oldpos + i >= 0) && (oldpos + i < oldsize)) {
                    newBuf[newpos + i] += oldBuf[oldpos + i];
                }
            }

            newpos += ctrl[0];
            oldpos += ctrl[0];

            if (newpos + ctrl[1] > newsize) {
                System.err.println("Corrupt patch");
                return;
            }

            if (!BinaryTools.readFromStream(extraBlockIn, newBuf, newpos, ctrl[1])) {
                System.err.println("error reading from extraIn");
                return;
            }

            newpos += ctrl[1];
            oldpos += ctrl[2];
        }

        diffBlockIn.close();
        extraBlockIn.close();
        diffIn.close();

        FileOutputStream out = new FileOutputStream(newFile);
        out.write(newBuf, 0, newBuf.length - 1);
        out.close();
    }
}