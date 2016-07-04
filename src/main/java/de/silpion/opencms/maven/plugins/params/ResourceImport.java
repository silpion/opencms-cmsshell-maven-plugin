package de.silpion.opencms.maven.plugins.params;

import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

/**
 * @author schrader
 */
public class ResourceImport {

    @Parameter(required = true)
    private File importFile;

    @Parameter(defaultValue = "/")
    private String importPath;

    @Parameter(defaultValue = "true")
    private boolean keepPermissions;

    public File getImportFile() {
        return importFile;
    }

    public String getImportPath() {
        return importPath;
    }

    public boolean isKeepPermissions() {
        return keepPermissions;
    }
}
