package de.silpion.opencms.maven.plugins;

import de.silpion.opencms.maven.plugins.params.CommandBuilder;
import de.silpion.opencms.maven.plugins.params.ResourceArtifact;
import de.silpion.opencms.maven.plugins.shell.SilpionCmsShell;
import de.silpion.opencms.maven.plugins.shell.SilpionCmsShellParameters;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactResolutionRequest;
import org.apache.maven.artifact.resolver.ArtifactResolutionResult;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.repository.RepositorySystem;
import org.opencms.file.CmsObject;
import org.opencms.main.CmsShell;
import org.opencms.main.I_CmsShellCommands;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

/**
 * @author schrader
 */
public abstract class AbstractCmsShellMojo extends AbstractMojo {

    @SuppressWarnings("unused")
    @Parameter(required = true, property = "cms.username")
    private String username;

    @SuppressWarnings("unused")
    @Parameter(required = true, property = "cms.password")
    private String password;

    @SuppressWarnings("unused")
    @Parameter(required = true, property = "cms.webInfPath")
    private File webInfPath;

    @SuppressWarnings("unused")
    @Parameter(property = "cms.servletMapping", defaultValue = "opencms")
    private String servletMapping;

    @SuppressWarnings("unused")
    @Parameter(property = "cms.defaultWebappName", defaultValue = "opencms")
    private String defaultWebappName;

    @SuppressWarnings("unused")
    @Parameter(defaultValue = "${user}@${project}:${siteroot}|${uri}>")
    private String prompt;

    @SuppressWarnings("unused")
    @Parameter(defaultValue = "true")
    private boolean verbose;

    @SuppressWarnings("unused")
    @Parameter(defaultValue = "${localRepository}", readonly = true, required = true)
    private ArtifactRepository localRepository;

    @SuppressWarnings("unused")
    @Parameter(defaultValue = "${project.remoteArtifactRepositories}", readonly = true, required = true)
    private List<ArtifactRepository> remoteRepos;

    @SuppressWarnings("unused")
    @Parameter(defaultValue = "false", property = "skipCmsShell")
    private boolean skipCmsShell;

    @Component
    private RepositorySystem repositorySystem;

    protected void resolveResources(Collection<ResourceArtifact> resources) throws MojoFailureException {
        // resolve artifacts or fail
        for (ResourceArtifact resource : resources) {
            ArtifactResolutionResult result = resolveResource(resource);
            if (!result.isSuccess()) {
                throw new MojoFailureException("Could not resolve: " + result.getMissingArtifacts());
            }
        }
    }

    protected ArtifactResolutionResult resolveResource(ResourceArtifact resource) {
        Artifact artifact = repositorySystem.createArtifact(resource.getGroupId(), resource.getArtifactId(),
                resource.getVersion(), "runtime", resource.getType());
        getLog().debug("Trying to resolve: " + artifact);

        return repositorySystem.resolve(new ArtifactResolutionRequest().setArtifact(artifact));
    }

    protected String getUsername() {
        return username;
    }

    protected String getPassword() {
        return password;
    }

    protected File getWebInfPath() {
        return webInfPath;
    }

    protected String getServletMapping() {
        return servletMapping;
    }

    protected String getDefaultWebappName() {
        return defaultWebappName;
    }

    protected String getPrompt() {
        return prompt;
    }

    public boolean isVerbose() {
        return verbose;
    }

    protected boolean isSkipCmsShell() {
        return skipCmsShell;
    }

    protected RepositorySystem getRepositorySystem() {
        return repositorySystem;
    }

    protected CmsShell createCmsShell() {
        I_CmsShellCommands additional = null;

        // TODO detect e.printStackTrace(out); -> fail
        PrintStream out = isVerbose() ? System.out : new PrintStream(new ByteArrayOutputStream(1024));
        PrintStream err = isVerbose() ? System.err : new PrintStream(new ByteArrayOutputStream(1024));
        boolean interactive = false;

        getLog().debug("Initialize CmsShell:");
        getLog().debug("\twebInfPath: '" + getWebInfPath().getAbsolutePath() + "'");
        getLog().debug("\tservletMapping: '" + getWebInfPath().getAbsolutePath() + "'");
        getLog().debug("\tdefaultWebappName: '" + getWebInfPath().getAbsolutePath() + "'");
        getLog().debug("\tadditional: '" + additional + "'");

        // #26110 - CmsShell-maven-plugin / NPE
        SilpionCmsShellParameters.init(out,err);

        return new SilpionCmsShell(getWebInfPath().getAbsolutePath(), getServletMapping(), getDefaultWebappName(),
                getPrompt(),
                additional,
                out, err,
                interactive
        );
    }

    public final void execute() throws MojoExecutionException, MojoFailureException {
        System.setProperty("org.slf4j.simpleLogger.log.org.apache.solr", "error");

        if (isSkipCmsShell()) {
            getLog().info("Skipping");
            return;
        }

        validate();

        getLog().info("Connecting " + getUsername() + "@************");

        CmsShell shell = createCmsShell();

        shell.execute(CommandBuilder.of("login")
                .param(getUsername())
                .param(getPassword()).get()
        );

        shell.execute(CommandBuilder.of("setCurrentProject")
                .param("Offline")
                .get()
        );

        shell.execute(CommandBuilder.of("setSiteRoot")
                .param("/")
                .get()
        );

        executeShellCommand(shell);

        shell.exit();
    }

    protected abstract void executeShellCommand(CmsShell shell) throws MojoFailureException;

    protected void validate() throws MojoFailureException {
        if (!webInfPath.exists()) {
            throw new MojoFailureException("Directory doesn't exists: '" + webInfPath.getAbsolutePath() + "'");
        }

        if (!webInfPath.isDirectory()) {
            throw new MojoFailureException("Must be a directory, not a file: '" + webInfPath.getAbsolutePath() + "'");
        }
    }

    protected CmsObject getCms(CmsShell shell) {
        try {
            Field field = CmsShell.class.getDeclaredField("m_cms");
            field.setAccessible(true);
            return (CmsObject) field.get(shell);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected void publishProjectAndWait(CmsShell shell) {
        shell.execute(CommandBuilder.of("publishProjectAndWait").get());

        // TODO to be verbose implement own command:
        // OpenCms.getPublishManager().publishProject(m_cms);
        // OpenCms.getPublishManager().waitWhileRunning();
    }
}
