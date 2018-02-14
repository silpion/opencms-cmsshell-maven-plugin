package de.silpion.opencms.maven.plugins.shell;

import java.io.PrintStream;
import java.util.Objects;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class SilpionCmsShellParameters {
    private static SilpionCmsShellParameters instance = new SilpionCmsShellParameters();

    private PrintStream out;
    private PrintStream err;

    public static SilpionCmsShellParameters getInstance() {
        return instance;
    }

    public static void init(PrintStream out, PrintStream err) {
        instance.out = Objects.requireNonNull(out, "out");
        instance.err = Objects.requireNonNull(err, "err");;
    }

    public PrintStream getOut() {
        return out;
    }

    public PrintStream getErr() {
        return err;
    }
}