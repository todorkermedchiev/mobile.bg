package bg.sofia.uni.fmi.dp.mobile.cli;

import bg.sofia.uni.fmi.dp.mobile.cli.command.CommandExecutor;
import bg.sofia.uni.fmi.dp.mobile.cli.command.CommandRegistry;

import java.io.PrintStream;
import java.util.Scanner;

public class CLI {
    private final CommandRegistry registry;
    private final Scanner scanner = new Scanner(System.in);
    private final PrintStream printer = System.out;

    private static final String EXIT_COMMAND = "exit";

    public CLI(CommandRegistry registry) {
        this.registry = registry;
    }

    public void start() {
        CommandExecutor executor = new CommandExecutor(registry);

        while (true) {
            System.out.print(">> ");
            String input = this.scanner.nextLine().trim();
            if (input.equalsIgnoreCase(EXIT_COMMAND)) {
                break;
            }

            try {
                String message = executor.execute(input);
                System.out.println(message);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

