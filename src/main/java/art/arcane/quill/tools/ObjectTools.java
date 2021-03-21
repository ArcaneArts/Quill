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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ObjectTools {
    public static void shove(Object data, Object into) throws BlackMagicException {
        if (!data.getClass().equals(into.getClass())) {
            throw new BlackMagicException("Classes must be the same");
        }

        for (Field i : data.getClass().getDeclaredFields()) {
            if (Modifier.isStatic(i.getModifiers())) {
                continue;
            }

            i.setAccessible(true);

            try {
                i.set(into, i.get(data));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
                throw new BlackMagicException("Reflection error");
            }
        }
    }
}
