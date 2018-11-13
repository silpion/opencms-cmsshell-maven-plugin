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
import de.silpion.opencms.maven.plugins.shell.CmsShell10_0_1;
import de.silpion.opencms.maven.plugins.shell.CommandExecutionException;
import de.silpion.opencms.maven.plugins.shell.I_CmsShell;
import de.silpion.opencms.maven.plugins.shell.I_CmsShellCommands;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
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
    @Parameter(defaultValue = "false")
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

    protected I_CmsShell createCmsShell() throws MojoFailureException {
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

        return new CmsShell10_0_1(getWebInfPath().getAbsolutePath(), getServletMapping(), getDefaultWebappName(),
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

        I_CmsShell shell = createCmsShell();

        try {
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
        } catch (CommandExecutionException e) {
            throw new MojoFailureException("Failed to execute command", e);
        } finally {
            shell.exit();
        }
    }

    protected abstract void executeShellCommand(I_CmsShell shell) throws CommandExecutionException, MojoFailureException;

    protected void validate() throws MojoFailureException {
        if (!webInfPath.exists()) {
            throw new MojoFailureException("Directory doesn't exists: '" + webInfPath.getAbsolutePath() + "'");
        }

        if (!webInfPath.isDirectory()) {
            throw new MojoFailureException("Must be a directory, not a file: '" + webInfPath.getAbsolutePath() + "'");
        }
    }

    protected void publishProjectAndWait(I_CmsShell shell) throws CommandExecutionException {
        shell.execute(CommandBuilder.of("publishProjectAndWait").get());

        // TODO to be verbose implement own command:
        // OpenCms.getPublishManager().publishProject(m_cms);
        // OpenCms.getPublishManager().waitWhileRunning();
    }
}
