package bg.sofia.uni.fmi.dp.mobile.cli.command.commands;

import bg.sofia.uni.fmi.dp.mobile.advertisement.Advertisement;
import bg.sofia.uni.fmi.dp.mobile.advertisement.AdvertisementService;
import bg.sofia.uni.fmi.dp.mobile.cli.localization.LocalizationService;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class SearchCommand implements Command {
    private static final String COMMAND_NAME_KEY = "command.search.name";
    private static final String COMMAND_DESCRIPTION_KEY = "command.search.description";

    private final AdvertisementService service;
    private final Scanner scanner;
    private final PrintStream printer;
    private final LocalizationService localization;

    public SearchCommand(AdvertisementService service, LocalizationService localization, Scanner scanner, PrintStream printer) {
        this.service = service;
        this.scanner = scanner;
        this.printer = printer;
        this.localization = localization;
    }

    @Override
    public void execute() {
        printer.print(localization.getMessage("prompt.enter.search.query"));
        String query = scanner.nextLine();

        List<Advertisement> results = service.searchAdvertisements(query);
        results.forEach(printer::println);
    }

    @Override
    public String getName() {
        return localization.getMessage(COMMAND_NAME_KEY);
    }

    @Override
    public String getDescription() {
        return localization.getMessage(COMMAND_DESCRIPTION_KEY);
    }
}

