package bg.sofia.uni.fmi.dp.mobile.cli.command;

import bg.sofia.uni.fmi.dp.mobile.cli.command.commands.Command;
import bg.sofia.uni.fmi.dp.mobile.cli.command.exception.UnknownCommandException;

import java.util.Objects;

public class CommandExecutor {
    private final CommandRegistry registry;

    public CommandExecutor(CommandRegistry registry) {
        this.registry = Objects.requireNonNull(registry);
    }

    public String execute(String commandName) {
        try {
            Command command = registry.getCommand(commandName);
            command.execute();
            return "Command " + commandName + " executed successfully!";
        } catch (UnknownCommandException e) {
            return "Unknown command";
        }
    }
}
