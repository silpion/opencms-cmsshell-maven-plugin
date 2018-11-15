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
