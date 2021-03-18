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
