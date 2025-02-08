package bg.sofia.uni.fmi.dp.mobile.cli.command;

import bg.sofia.uni.fmi.dp.mobile.cli.command.commands.Command;
import bg.sofia.uni.fmi.dp.mobile.cli.command.exception.UnknownCommandException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommandRegistry {
    private final Map<String, Command> commands;

    public CommandRegistry() {
        this(new HashMap<>());
    }

    public CommandRegistry(Map<String, Command> commands) {
        this.commands = Objects.requireNonNull(commands, "Commands map cannot be null.");
    }

    public void addCommand(Command command) {
        Objects.requireNonNull(command, "Command cannot be null.");
        commands.put(command.getName(), command);
    }

    public Command getCommand(String commandName) {
        if (!commands.containsKey(commandName)) {
            throw new UnknownCommandException("The command " + commandName + " does not exist");
        }
        return commands.get(commandName);
    }

    public Collection<Command> getAllCommands() {
        return commands.values();
    }
}