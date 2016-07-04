package de.silpion.opencms.maven.plugins;

import de.silpion.opencms.maven.plugins.params.CommandBuilder;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.opencms.main.CmsShell;

import java.util.List;

/**
 * see CmsShellCommands#deleteResource(java.lang.String)
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