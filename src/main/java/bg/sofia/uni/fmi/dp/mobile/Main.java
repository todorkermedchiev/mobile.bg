package bg.sofia.uni.fmi.dp.mobile;

import bg.sofia.uni.fmi.dp.mobile.advertisement.AdvertisementRepository;
import bg.sofia.uni.fmi.dp.mobile.advertisement.AdvertisementService;
import bg.sofia.uni.fmi.dp.mobile.advertisement.InMemoryAdRepository;
import bg.sofia.uni.fmi.dp.mobile.cli.CLI;
import bg.sofia.uni.fmi.dp.mobile.cli.command.CommandRegistry;
import bg.sofia.uni.fmi.dp.mobile.cli.command.commands.AddAdvertisementCommand;
import bg.sofia.uni.fmi.dp.mobile.cli.command.commands.SearchCommand;
import bg.sofia.uni.fmi.dp.mobile.cli.command.commands.SubscribeCommand;
import bg.sofia.uni.fmi.dp.mobile.notification.NotificationService;
import bg.sofia.uni.fmi.dp.mobile.parser.QueryFilterCreator;
import bg.sofia.uni.fmi.dp.mobile.parser.RPNQueryFilterCreator;
import bg.sofia.uni.fmi.dp.mobile.parser.RPNQueryParser;
import bg.sofia.uni.fmi.dp.mobile.parser.RPNSearcher;
import bg.sofia.uni.fmi.dp.mobile.parser.Searcher;

import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    private static final PrintStream PRINTER = System.out;
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        AdvertisementRepository advertisementRepository = new InMemoryAdRepository();
        QueryFilterCreator queryFilterCreator = new RPNQueryFilterCreator(new RPNQueryParser());
        Searcher searcher = new RPNSearcher(queryFilterCreator);
        NotificationService notificationService = new NotificationService();
        AdvertisementService advertisementService = new AdvertisementService(advertisementRepository, searcher, notificationService);

        CommandRegistry commandRegistry = new CommandRegistry();
        commandRegistry.addCommand(new AddAdvertisementCommand(advertisementService, SCANNER, PRINTER));
        commandRegistry.addCommand(new SearchCommand(advertisementService, SCANNER, PRINTER));
        commandRegistry.addCommand(new SubscribeCommand(advertisementService, queryFilterCreator, SCANNER, PRINTER));

        CLI cli = new CLI(commandRegistry);
        cli.start();
    }
}
