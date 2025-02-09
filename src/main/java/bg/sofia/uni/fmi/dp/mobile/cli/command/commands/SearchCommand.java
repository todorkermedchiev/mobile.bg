package bg.sofia.uni.fmi.dp.mobile.cli.command.commands;

import bg.sofia.uni.fmi.dp.mobile.advertisement.Advertisement;
import bg.sofia.uni.fmi.dp.mobile.advertisement.AdvertisementService;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class SearchCommand implements Command {
    private static final String COMMAND_NAME = "search";

    private final AdvertisementService service;
    private final Scanner scanner;
    private final PrintStream printer;

    public SearchCommand(AdvertisementService service, Scanner scanner, PrintStream printer) {
        this.service = service;
        this.scanner = scanner;
        this.printer = printer;
    }

    @Override
    public void execute() {
        printer.print("Enter search query: ");
        String query = scanner.nextLine();

        List<Advertisement> results = service.searchAdvertisements(query);
        results.forEach(printer::println);
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Search for advertisements";
    }
}

