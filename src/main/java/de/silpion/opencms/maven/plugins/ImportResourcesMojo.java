package de.silpion.opencms.maven.plugins;

import de.silpion.opencms.maven.plugins.params.CommandBuilder;
import de.silpion.opencms.maven.plugins.params.ResourceArtifact;
import de.silpion.opencms.maven.plugins.params.ResourceImport;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.opencms.main.CmsShell;

/**
 * see CmsShellCommands#importResources(java.lang.String, java.lang.String, boolean)
 */
@Mojo(name = "importResources",
        defaultPhase = LifecyclePhase.INSTALL,
        requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME
)
public class ImportResourcesMojo extends AbstractImportMojo {

    @Parameter(defaultValue = "true")
    private boolean publishAfter;

    @Override
    protected void executeShellCommand(CmsShell shell) throws MojoFailureException {

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