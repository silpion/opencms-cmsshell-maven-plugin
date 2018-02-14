package de.silpion.opencms.maven.plugins.params;

/**
 * @author schrader
 */
public class CommandBuilder {

    public static CommandBuilder of(String method) {
        CommandBuilder commandBuilder = new CommandBuilder();
        commandBuilder.line.append(method);

        return commandBuilder;
    }

    private StringBuilder line = new StringBuilder(48);

    public CommandBuilder param(String p) {
        line.append(" '").append(p).append("'");
        return this;
    }

    public CommandBuilder param(boolean p) {
        line.append(" ").append(p).append("");
        return this;
    }

    public String get() {
        return line.toString();
    }

}