package de.silpion.opencms.maven.plugins.shell;

import java.io.PrintStream;

public class SilpionCmsShellParameters {
    private static SilpionCmsShellParameters instance = new SilpionCmsShellParameters();

    private PrintStream out;
    private PrintStream err;

    public static SilpionCmsShellParameters getInstance() {
        return instance;
    }

    public static void checkInitialization() {
        if (instance.out == null || instance.err == null) {
            throw new NotInitializedException();
        }
    }

    public static void init(PrintStream out, PrintStream err) {
        if (instance.out == null && instance.err == null) {
            instance.out = out;
            instance.err = err;
        } else {
            throw new AlreadyInitializedException();
        }
    }

    public PrintStream getOut() {
        return out;
    }

    public PrintStream getErr() {
        return err;
    }
}
