package de.silpion.opencms.maven.plugins.shell;

import org.opencms.main.CmsShell;
import org.opencms.main.I_CmsShellCommands;

import java.io.PrintStream;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class SilpionCmsShell extends CmsShell {

    public SilpionCmsShell(String webInfPath, String servletMapping, String defaultWebAppName, String prompt, I_CmsShellCommands additionalShellCommands, PrintStream out, PrintStream err, boolean interactive) {
        super(webInfPath, servletMapping, defaultWebAppName, prompt, additionalShellCommands, out, err, interactive);
    }
}