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
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.opencms.configuration.CmsConfigurationException;
import org.opencms.main.CmsShell;
import org.opencms.module.CmsModule;
import org.opencms.module.CmsModuleImportExportHandler;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * <p/>
 * see org.opencms.main.CmsShellCommands9_5_2#replaceModule(java.lang.String, java.lang.String)
 */
@Mojo(name = "replaceModule",
        defaultPhase = LifecyclePhase.INSTALL,
        requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME
)
public class ReplaceModuleMojo extends AbstractImportMojo {

    @Override
    protected void executeShellCommand(CmsShell shell) throws MojoFailureException {
        try {
            for (ResourceImport file : getFiles()) {
                getLog().info("Install '" + file.getImportFile());
                if (!file.getImportFile().exists()) {
                    String msg = String.format("File '%s' does not exists", file.getImportFile().getAbsolutePath());
                    throw new MojoFailureException(msg, new FileNotFoundException());
                }
                shell.execute(CommandBuilder.of("replaceModule")
                        .param(getModuleName(file.getImportFile()))
                        .param(getAbsolutePath(file.getImportFile()))
                        .get()
                );
            }

            for (ResourceArtifact resource : getArtifacts()) {
                Artifact artifact = resolveResource(resource).getArtifacts().iterator().next();

                getLog().info("Install '" + artifact.getFile());
                shell.execute(CommandBuilder.of("replaceModule")
                        .param(getModuleName(artifact.getFile()))
                        .param(getAbsolutePath(artifact.getFile()))
                        .get()
                );
            }
        } catch (CmsConfigurationException e) {
            throw new MojoFailureException("Failed to read module meta data", e);
        }
    }

    private String getModuleName(File importFile) throws CmsConfigurationException {
        CmsModule module = CmsModuleImportExportHandler.readModuleFromImport(getAbsolutePath(importFile));
        return module.getName();
    }

}
