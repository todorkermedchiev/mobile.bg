package bg.sofia.uni.fmi.dp.mobile.cli.command.commands;

public interface Command {
    void execute();
    String getName();
    String getDescription();
}
