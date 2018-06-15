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
import de.silpion.opencms.maven.plugins.params.ResourceArtifact;
import de.silpion.opencms.maven.plugins.params.ResourceImport;
import de.silpion.opencms.maven.plugins.shell.CommandExecutionException;
import de.silpion.opencms.maven.plugins.shell.I_CmsShell;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

/**
 * see CmsShellCommands10_5_2#importResources(java.lang.String, java.lang.String, boolean)
 */
@Mojo(name = "importResources",
        defaultPhase = LifecyclePhase.INSTALL,
        requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME
)
@SuppressWarnings("unused")
public class ImportResourcesMojo extends AbstractImportMojo {

    @Parameter(defaultValue = "true")
    private boolean publishAfter;

    @Override
    protected void executeShellCommand(I_CmsShell shell) throws CommandExecutionException {

        // TODO create a temp project

        for (ResourceImport file : getFiles()) {
            getLog().info("Import '" + file.getImportFile() + "' into '" + file.getImportPath() + "'");
            shell.execute(CommandBuilder.of("importResources")
                    .param(getAbsolutePath(file.getImportFile()))
                    .param(file.getImportPath())
                    .param(file.isKeepPermissions())
                    .get()
            );
        }

        for (ResourceArtifact resource : getArtifacts()) {
            Artifact artifact = resolveResource(resource).getArtifacts().iterator().next();

            getLog().info("Import '" + artifact.getFile() + "' into '/'");
            shell.execute(CommandBuilder.of("importResources")
                    .param(getAbsolutePath(artifact.getFile()))
                    .param(resource.getImportPath())
                    .param(resource.isKeepPermissions())
                    .get()
            );
        }

        if (publishAfter) {
            publishProjectAndWait(shell);
        }
    }
}
