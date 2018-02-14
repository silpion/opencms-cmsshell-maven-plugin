package de.silpion.opencms.maven.plugins.params;

import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author schrader
 */
public class ResourceArtifact {

    @Parameter(required = true)
    private String groupId;

    @Parameter(required = true)
    private String artifactId;

    @Parameter(required = true)
    private String version;

    @Parameter(defaultValue = "zip")
    private String type = "zip";

    @Parameter(defaultValue = "/")
    private String importPath;

    @Parameter(defaultValue = "true")
    private boolean keepPermissions;

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    public String getType() {
        return type;
    }

    public String getImportPath() {
        return importPath;
    }

    public boolean isKeepPermissions() {
        return keepPermissions;
    }
}