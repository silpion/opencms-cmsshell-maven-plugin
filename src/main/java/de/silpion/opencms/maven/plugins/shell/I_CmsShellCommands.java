package de.silpion.opencms.maven.plugins.shell;

import org.opencms.file.CmsObject;

/**
 * @author brandt
 */
public interface I_CmsShellCommands {
    /**
     * Provides access to the shell CmsObject and the shell itself.<p>
     *
     * @param cms   the shell CmsObject
     * @param shell the CmsShell
     */
    void initShellCmsObject(CmsObject cms, I_CmsShell shell);

    /**
     * May be called after shell exit, can e.g. be used to ouput a goodbye message.<p>
     * <p>
     * Please note: This method is not guaranteed to be called. For a shell that has more then
     * one shell command object initialized, only the exit method of one of thouse will be called.<p>
     */
    void shellExit();

    /**
     * May be called before shell startup, can e.g. be used to ouput a welcome message.<p>
     * <p>
     * Please note: This method is not guaranteed to be called. For a shell that has more then
     * one shell command object initialized, only the start method of one of thouse will be called.<p>
     */
    void shellStart();
}
