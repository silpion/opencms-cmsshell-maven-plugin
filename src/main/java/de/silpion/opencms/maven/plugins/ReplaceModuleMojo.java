package de.silpion.opencms.maven.plugins;

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
import org.opencms.module.CmsModuleImportExportRepository;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * If we need more sophisticated control over module import, we may use: {@link CmsModuleImportExportRepository}.
 * <p/>
 * see org.opencms.main.CmsShellCommands#replaceModule(java.lang.String, java.lang.String)
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