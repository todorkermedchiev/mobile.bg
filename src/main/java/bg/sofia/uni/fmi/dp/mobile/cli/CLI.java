package bg.sofia.uni.fmi.dp.mobile.cli;

import bg.sofia.uni.fmi.dp.mobile.cli.command.CommandExecutor;
import bg.sofia.uni.fmi.dp.mobile.cli.command.CommandRegistry;
import bg.sofia.uni.fmi.dp.mobile.cli.localization.LocalizationService;

import java.io.PrintStream;
import java.util.Scanner;

public class CLI {
    private final CommandRegistry registry;
    private final LocalizationService localizationService;
    private final Scanner scanner;
    private final PrintStream printer;

    public CLI(CommandRegistry registry, LocalizationService localization) {
        this(registry, localization, new Scanner(System.in), System.out);
    }

    public CLI(CommandRegistry registry, LocalizationService localization, Scanner scanner, PrintStream printer) {
        this.registry = registry;
        this.localizationService = localization;
        this.printer = printer;
        this.scanner = scanner;
    }

    public void start() {
        CommandExecutor executor = new CommandExecutor(registry, localizationService);

        while (true) {
            printer.print(">> ");
            String input = this.scanner.nextLine().trim();
            if (input.equalsIgnoreCase(localizationService.getMessage("command.exit"))) {
                break;
            }

            try {
                String message = executor.execute(input);
                printer.println(message);
            } catch (RuntimeException e) {
                printer.println(e.getMessage());
            }
        }
    }
}

