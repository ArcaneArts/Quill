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

public interface NBTRawMaxDepthIO {

    default int decrementMaxDepth(int maxDepth) {
        if (maxDepth < 0) {
            throw new IllegalArgumentException("negative maximum depth is not allowed");
        } else if (maxDepth == 0) {
            throw new NBTRawMaxDepthReachedException("reached maximum depth of NBT structure");
        }
        return --maxDepth;
    }
}
