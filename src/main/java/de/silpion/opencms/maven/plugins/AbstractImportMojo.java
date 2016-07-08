package de.silpion.opencms.maven.plugins;

import de.silpion.opencms.maven.plugins.params.ResourceArtifact;
import de.silpion.opencms.maven.plugins.params.ResourceImport;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

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
}
