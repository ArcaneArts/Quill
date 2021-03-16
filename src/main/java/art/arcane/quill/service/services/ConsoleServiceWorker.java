package art.arcane.quill.service;

import art.arcane.quill.Quill;
import art.arcane.quill.collections.KList;
import art.arcane.quill.collections.KMap;
import art.arcane.quill.execution.J;
import art.arcane.quill.format.Form;
import art.arcane.quill.io.StreamSucker;
import art.arcane.quill.logging.L;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The console worker is used to manage console commands. You can register commands for your service here.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConsoleServiceWorker extends QuillServiceWorker {
    private long activeFlushInterval = 250;
    private long idleFlushInterval = 1000;
    private boolean verbose = true;
    private boolean deduplicateLines = true;
    private boolean allowCommands = true;
    private boolean shittyConsole = false;
    private transient KMap<String, CMD> commands;
    private static final String blank = Form.repeat("    ", 10);

    public ConsoleServiceWorker() {
        super();
        commands = new KMap<>();
        this.shittyConsole = false;
    }

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
