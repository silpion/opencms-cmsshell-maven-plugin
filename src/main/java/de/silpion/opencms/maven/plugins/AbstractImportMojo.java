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

import de.silpion.opencms.maven.plugins.params.ResourceArtifact;
import de.silpion.opencms.maven.plugins.params.ResourceImport;

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * @author schrader
 */
public abstract class AbstractImportMojo extends AbstractCmsShellMojo {

  @Parameter
  private List<ResourceImport> files = Collections.emptyList();

  @Parameter
  private List<ResourceArtifact> artifacts = Collections.emptyList();

  protected List<ResourceImport> getFiles() {
    return files;
  }

  protected List<ResourceArtifact> getArtifacts() {
    return artifacts;
  }

  @Override
  protected void validate() throws MojoFailureException {
    super.validate();

    if (files.isEmpty() && artifacts.isEmpty()) {
      throw new MojoFailureException("A file to import must be set");
    }

    resolveResources(artifacts);
  }

  protected String getAbsolutePath(File file) {
    String path = file.getAbsolutePath();
    return path.replace('\\','/');
  }
}
