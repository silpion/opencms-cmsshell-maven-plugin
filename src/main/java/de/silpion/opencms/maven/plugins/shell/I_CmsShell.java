package de.silpion.opencms.maven.plugins.shell;

import org.opencms.db.CmsUserSettings;
import org.opencms.file.CmsObject;
import org.opencms.i18n.CmsMessages;
import org.opencms.main.CmsException;

import java.io.PrintStream;
import java.util.Locale;

/**
 * @author brandt
 */
public interface I_CmsShell {
    PrintStream getOut();

    void setEcho(boolean echo);

    void exit();

    void help(String searchString);

    CmsUserSettings initSettings();

    void setPrompt(String prompt);

    void setLocale(Locale locale) throws CmsException;

    boolean isInteractive();

    CmsMessages getMessages();

    void execute(String commands) throws CommandExecutionException;

    CmsObject getCms();
}
