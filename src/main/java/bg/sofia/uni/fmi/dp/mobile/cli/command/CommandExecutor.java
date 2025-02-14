package bg.sofia.uni.fmi.dp.mobile.cli.command;

import bg.sofia.uni.fmi.dp.mobile.cli.command.commands.Command;
import bg.sofia.uni.fmi.dp.mobile.cli.command.exception.UnknownCommandException;
import bg.sofia.uni.fmi.dp.mobile.cli.localization.LocalizationService;

import java.util.Objects;

public class CommandExecutor {
    private final CommandRegistry registry;
    private final LocalizationService localizationService;

    public CommandExecutor(CommandRegistry registry, LocalizationService localization) {
        this.registry = registry;
        this.localizationService = localization;
    }

    public String execute(String commandName) {
        try {
            Command command = registry.getCommand(commandName);
            command.execute();
            return String.format(localizationService.getMessage("message.successful.command.format"), commandName);
        } catch (UnknownCommandException e) {
            return localizationService.getMessage("error.unknown.command");
        }
    }
}
