package bg.sofia.uni.fmi.dp.mobile.cli.command.commands;

public class ExitCommand implements Command {
    private static final String COMMAND_NAME = "exit";

    @Override
    public void execute() {
        System.out.println("Exiting the system...");
        System.exit(0);
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Exit the system";
    }
}

