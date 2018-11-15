package de.silpion.opencms.maven.plugins.params;

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

/**
 * @author schrader
 */
public class CommandBuilder {

    public static CommandBuilder of(String method) {
        CommandBuilder commandBuilder = new CommandBuilder();
        commandBuilder.line.append(method);

        return commandBuilder;
    }

    private StringBuilder line = new StringBuilder(48);

    public CommandBuilder param(String p) {
        line.append(" '").append(p).append("'");
        return this;
    }

    public CommandBuilder param(boolean p) {
        line.append(" ").append(p).append("");
        return this;
    }

    public String get() {
        return line.toString();
    }

}
