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

import art.arcane.quill.Quill;
import art.arcane.quill.execution.J;
import art.arcane.quill.logging.L;
import art.arcane.quill.service.QuillService;
import art.arcane.quill.service.util.CMD;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServiceTest
{
    private QuillTestService testService;

    @BeforeAll
    public void startupServices()
    {
        QuillTestService.startTestService();
        testService = (QuillTestService) Quill.delegate;
        L.v("Ready to test!");
    }

    @Test
    public void testCommandRegistry()
    {
        L.v("Command Tests");
        AtomicReference<String> res = new AtomicReference<>("");
        testService.getConsole().registerCommand("test", new CMD() {
            @Override
            public boolean onCommand(String... params) {
                res.set(params[0] + " " + params[1]);
                return true;
            }
        });
        testService.getConsole().process("test a44 b1");
        assertEquals("a44 b1", res.get(), "Command Parameter failure. Entered 'test a44 b1' but parameters received was " + res.get());
        testService.getConsole().unregisterCommand("test");
    }

    @Test
    public void testTaskAtomics()
    {
        testService.getScheduler().setVerbose(true);
        testService.getScheduler().setBurstExecutionThreshold(10001);
        AtomicInteger v = new AtomicInteger(0);

        for(int i = 0; i < 10000; i++)
        {
            L.v("Created Queued Task");
            testService.getScheduler().queue(() -> {
                v.addAndGet(1);
                L.v("Executed Task");
            });
        }

        J.attempt(() -> testService.getScheduler().whenCompleted().get());
        assertEquals(v.get(), 10000, "Executed 10,000 tasks but the result counter shows " + v.get());
    }

    @AfterAll
    public void shutdownServices()
    {
        L.v("Shutting down services");
        Quill.shutdown();
    }
}
