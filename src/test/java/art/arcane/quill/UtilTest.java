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

package art.arcane.quill;

import art.arcane.quill.format.Form;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilTest {
    @Test
    public void testFormatting()
    {
        assertEquals("34%", Form.pc(0.3433366));
        assertEquals("32.235%", Form.pc(0.322345667, 3));
        assertEquals("100,000", Form.f(100000));
        assertEquals("-100,000", Form.f(-100000));
        assertEquals("100,000", Form.f(100000L));
        assertEquals("-100,000", Form.f(-100000L));
        assertEquals("4.2000", Form.fd(4.2, 4));
    }
}
