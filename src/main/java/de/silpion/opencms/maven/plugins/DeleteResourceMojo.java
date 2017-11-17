package de.silpion.opencms.maven.plugins;

import de.silpion.opencms.maven.plugins.params.CommandBuilder;
import de.silpion.opencms.maven.plugins.shell.CommandExecutionException;
import de.silpion.opencms.maven.plugins.shell.I_CmsShell;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.util.List;

/**
 * see CmsShellCommands#deleteResource(java.lang.String)
 */
@Mojo(name = "deleteResource",
        defaultPhase = LifecyclePhase.INSTALL,
        requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME
)
@SuppressWarnings("unused")
public class DeleteResourceMojo extends AbstractCmsShellMojo {

    @Parameter(required = true)
    private List<String> resourceNames;

    @Parameter(defaultValue = "true")
    private boolean publishAfter;

    @Override
    protected void executeShellCommand(I_CmsShell shell) throws CommandExecutionException {

        // TODO create a temp project

        for (String resourceName : resourceNames) {
            if (!shell.getCms().existsResource(resourceName)) {
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