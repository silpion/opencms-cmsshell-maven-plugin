package de.silpion.opencms.maven.plugins;

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

import de.silpion.opencms.maven.plugins.params.CommandBuilder;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.opencms.main.CmsShell;

import java.util.List;

/**
 * see CmsShellCommands9_5_2#deleteResource(java.lang.String)
 */
@Mojo(name = "deleteResource",
        defaultPhase = LifecyclePhase.INSTALL,
        requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME
)
public class DeleteResourceMojo extends AbstractCmsShellMojo {

    @Parameter(required = true)
    private List<String> resourceNames;

    @Parameter(defaultValue = "true")
    private boolean publishAfter;

    @Override
    protected void executeShellCommand(CmsShell shell) {

        // TODO create a temp project

        for (String resourceName : resourceNames) {
            if (!getCms(shell).existsResource(resourceName)) {
                getLog().info("Skip delete, '" + resourceName + "' doesn't exists!");
                continue;
            }

            getLog().info("Delete '" + resourceName + "'");
            shell.execute(CommandBuilder.of("deleteResource")
                    .param(resourceName).get()
            );
        }

        if (publishAfter) {
            publishProjectAndWait(shell);
        }
    }
}
