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

package art.arcane.quill.service.services;

import art.arcane.quill.Quill;
import art.arcane.quill.collections.KList;
import art.arcane.quill.collections.KMap;
import art.arcane.quill.execution.J;
import art.arcane.quill.format.Form;
import art.arcane.quill.io.StreamSucker;
import art.arcane.quill.logging.L;
import art.arcane.quill.service.QuillService;
import art.arcane.quill.service.util.CMD;
import lombok.*;

/**
 * The console worker is used to manage console commands. You can register commands for your service here.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsoleService extends QuillService {
    private static final String blank = Form.repeat("    ", 10);
    private transient final KMap<String, CMD> commands = new KMap<>();
    @Builder.Default
    private long activeFlushInterval = 250;
    @Builder.Default
    private long idleFlushInterval = 1000;
    @Builder.Default
    private boolean verbose = true;
    @Builder.Default
    private boolean deduplicateLines = true;
    @Builder.Default
    private boolean allowCommands = true;
    @Builder.Default
    private boolean shittyConsole = false;

    @Override
    public void onEnable() {
        registerInternalCommands();
        prompt();

        J.a(() ->
        {
            J.sleep(3000);
            if (shittyConsole) {
                L.w("Bad Console is being used. Expect Garbage.");
            }
        });

        L.consoleConsumer = this::log;
        L.ACTIVE_FLUSH_INTERVAL = getActiveFlushInterval();
        L.IDLE_FLUSH_INTERVAL = getIdleFlushInterval();
        L.DEDUPLICATE_LOGS = isDeduplicateLines();
        L.VERBOSE = isVerbose();
        new StreamSucker(System.in, this::process);
    }

    @Override
    public void onDisable() {

    }

    private void registerInternalCommands() {
        registerCommand("stop", (args) ->
        {
            Quill.shutdown();
            return true;
        });

        registerCommand("help", (args) ->
        {
            for (String i : commands.k()) {
                L.i("- " + i);
            }

            return true;
        });
    }

    /**
     * You can call this to force process a command as if it were entered in the console.
     *
     * @param s the command.
     */
    public void process(String s) {
        prompt();

        if (s.trim().isEmpty()) {
            return;
        }

        KList<String> params = new KList<String>();
        String command = s;

        if (s.contains(" ")) {
            params = new KList<String>(s.split("\\Q \\E"));
            command = params.pop();
        }

        processCommand(command, params.toArray(new String[0]));
    }

    /**
     * Register a command (the root command name, and the command object)
     *
     * @param string the root command
     * @param cmd    the command executor
     */
    public void registerCommand(String string, CMD cmd) {
        commands.put(string, cmd);
    }

    public void unregisterCommand(String string) {
        commands.remove(string);
    }

    private void processCommand(String command, String... params) {
        if (!allowCommands) {
            return;
        }

        try {
            for (String i : commands.k()) {
                if (command.trim().toLowerCase().equals(i.toLowerCase())) {
                    if (commands.get(i).onCommand(params)) {
                        L.flush();
                        return;
                    }
                }
            }

            L.i("Unknown Command");
            L.flush();
        } catch (Throwable e) {
            L.f("Failed to execute command");
            L.ex(e);
        }
    }

    private void log(String s) {
        if (shittyConsole) {
            System.out.println(s);
        } else {
            rewrite(s.replaceAll("\\Q\n\\E", "").replaceAll("\\Q\r\\E", ""));
            ln();
            prompt();
        }
    }

    private void prompt() {
        if (!shittyConsole) {
            print("> ");
        }
    }

    private void ln() {
        if (!shittyConsole) {
            System.out.println();
        }
    }

    private void print(String s) {
        if (!shittyConsole) {
            System.out.print(s);
        }
    }

    private void rewrite(String s) {
        if (!shittyConsole) {
            System.out.print("\r" + s + blank);
        }
    }
}
