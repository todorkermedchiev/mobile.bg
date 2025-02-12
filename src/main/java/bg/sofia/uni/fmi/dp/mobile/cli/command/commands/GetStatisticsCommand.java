package bg.sofia.uni.fmi.dp.mobile.cli.command.commands;

import bg.sofia.uni.fmi.dp.mobile.advertisement.AdvertisementService;
import bg.sofia.uni.fmi.dp.mobile.parser.QueryFilterCreator;

import java.io.PrintStream;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GetStatisticsCommand implements Command {
    private static final String COMMAND_NAME = "get-stats";
    private static final String COMMAND_DESCRIPTION = "Get statistics for the vehicle price through the years";

    private final AdvertisementService service;
    private final QueryFilterCreator queryFilterCreator;
    private final Scanner scanner;
    private final PrintStream printer;

    public GetStatisticsCommand(AdvertisementService service, QueryFilterCreator queryFilterCreator, Scanner scanner, PrintStream printer) {
        this.service = service;
        this.queryFilterCreator = queryFilterCreator;
        this.scanner = scanner;
        this.printer = printer;
    }

    @Override
    public void execute() {
        printer.print("Enter query for the vehicle: ");
        String query = scanner.nextLine();

        Map<Year, Double> results = service.getPriceStats(List.of(queryFilterCreator.create(query)));
        results.forEach((k, v) -> printer.printf("%d -> %f\n", k.getValue(), v));
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }
}
