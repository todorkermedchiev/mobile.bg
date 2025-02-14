package bg.sofia.uni.fmi.dp.mobile.cli.command.commands;

import bg.sofia.uni.fmi.dp.mobile.advertisement.AdvertisementService;
import bg.sofia.uni.fmi.dp.mobile.cli.localization.LocalizationService;
import bg.sofia.uni.fmi.dp.mobile.parser.QueryFilterCreator;

import java.io.PrintStream;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GetStatisticsCommand implements Command {
    private static final String COMMAND_NAME_KEY = "command.stats.name";
    private static final String COMMAND_DESCRIPTION_KEY = "command.stats.description";

    private final AdvertisementService service;
    private final QueryFilterCreator queryFilterCreator;
    private final Scanner scanner;
    private final PrintStream printer;
    private final LocalizationService localization;

    public GetStatisticsCommand(AdvertisementService service, QueryFilterCreator queryFilterCreator, LocalizationService localization, Scanner scanner, PrintStream printer) {
        this.service = service;
        this.queryFilterCreator = queryFilterCreator;
        this.scanner = scanner;
        this.printer = printer;
        this.localization = localization;
    }

    @Override
    public void execute() {
        printer.print(localization.getMessage("prompt.enter.stats.query"));
        String query = scanner.nextLine();

        Map<Year, Double> results = service.getPriceStats(List.of(queryFilterCreator.create(query)));
        results.forEach((k, v) -> printer.printf("%d -> %.2f\n", k.getValue(), v));
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
