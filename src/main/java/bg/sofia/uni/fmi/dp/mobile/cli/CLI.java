package bg.sofia.uni.fmi.dp.mobile.cli;

import bg.sofia.uni.fmi.dp.mobile.cli.command.CommandExecutor;
import bg.sofia.uni.fmi.dp.mobile.cli.command.CommandRegistry;

import java.io.PrintStream;
import java.util.Scanner;

public class CLI {
    private final CommandRegistry registry;
    private final Scanner scanner;
    private final PrintStream printer;

    private static final String EXIT_COMMAND = "exit";

    public CLI(CommandRegistry registry) {
        this(registry, new Scanner(System.in), System.out);
    }

    public CLI(CommandRegistry registry, Scanner scanner, PrintStream printer) {
        this.registry = registry;
        this.printer = printer;
        this.scanner = scanner;
    }

    public void start() {
        CommandExecutor executor = new CommandExecutor(registry);

        while (true) {
            printer.print(">> ");
            String input = this.scanner.nextLine().trim();
            if (input.equalsIgnoreCase(EXIT_COMMAND)) {
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

