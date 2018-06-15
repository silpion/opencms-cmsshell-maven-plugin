package de.silpion.opencms.maven.plugins.shell;

/*-
 * #%L
 * OpenCms CmsShell Maven Plugin
 * %%
 * Copyright (C) 2017 - 2018 Silpion IT-Solutions GmbH (https://www.silpion.de/)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

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
