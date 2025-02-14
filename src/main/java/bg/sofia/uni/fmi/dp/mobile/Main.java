package bg.sofia.uni.fmi.dp.mobile;

import bg.sofia.uni.fmi.dp.mobile.advertisement.AdvertisementRepository;
import bg.sofia.uni.fmi.dp.mobile.advertisement.AdvertisementService;
import bg.sofia.uni.fmi.dp.mobile.advertisement.InMemoryAdRepository;
import bg.sofia.uni.fmi.dp.mobile.cli.CLI;
import bg.sofia.uni.fmi.dp.mobile.cli.command.CommandRegistry;
import bg.sofia.uni.fmi.dp.mobile.cli.command.commands.AddAdvertisementCommand;
import bg.sofia.uni.fmi.dp.mobile.cli.command.commands.GetStatisticsCommand;
import bg.sofia.uni.fmi.dp.mobile.cli.command.commands.SearchCommand;
import bg.sofia.uni.fmi.dp.mobile.cli.command.commands.SubscribeCommand;
import bg.sofia.uni.fmi.dp.mobile.cli.localization.LocalizationService;
import bg.sofia.uni.fmi.dp.mobile.notification.NotificationService;
import bg.sofia.uni.fmi.dp.mobile.parser.QueryFilterCreator;
import bg.sofia.uni.fmi.dp.mobile.parser.RPNQueryFilterCreator;
import bg.sofia.uni.fmi.dp.mobile.parser.RPNQueryParser;
import bg.sofia.uni.fmi.dp.mobile.parser.RPNSearcher;
import bg.sofia.uni.fmi.dp.mobile.parser.Searcher;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        PrintStream printer = System.out;
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        printer.println("Choose language (en/bg): ");
        String lang = scanner.nextLine();
        Locale locale = lang.equalsIgnoreCase("bg") ? new Locale("bg", "BG") : new Locale("en", "US");

        LocalizationService localizationService = new LocalizationService(locale);
        AdvertisementRepository advertisementRepository = new InMemoryAdRepository();
        QueryFilterCreator queryFilterCreator = new RPNQueryFilterCreator(new RPNQueryParser());
        Searcher searcher = new RPNSearcher(queryFilterCreator);
        NotificationService notificationService = new NotificationService();
        AdvertisementService advertisementService = new AdvertisementService(advertisementRepository, searcher, notificationService);

        CommandRegistry commandRegistry = new CommandRegistry();
        commandRegistry.addCommand(new AddAdvertisementCommand(advertisementService, localizationService, scanner, printer));
        commandRegistry.addCommand(new SearchCommand(advertisementService, localizationService, scanner, printer));
        commandRegistry.addCommand(new SubscribeCommand(advertisementService, queryFilterCreator, localizationService, scanner, printer));
        commandRegistry.addCommand(new GetStatisticsCommand(advertisementService, queryFilterCreator, localizationService, scanner, printer));

        CLI cli = new CLI(commandRegistry, localizationService);
        try {
            cli.start();
        } catch (Throwable t) {
            printer.println(t.getMessage());
        }
    }
}
