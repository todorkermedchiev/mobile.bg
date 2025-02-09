package bg.sofia.uni.fmi.dp.mobile;

import bg.sofia.uni.fmi.dp.mobile.advertisement.Advertisement;
import bg.sofia.uni.fmi.dp.mobile.advertisement.AdvertisementRepository;
import bg.sofia.uni.fmi.dp.mobile.advertisement.AdvertisementService;
import bg.sofia.uni.fmi.dp.mobile.advertisement.InMemoryAdRepository;
import bg.sofia.uni.fmi.dp.mobile.cli.CLI;
import bg.sofia.uni.fmi.dp.mobile.cli.command.CommandRegistry;
import bg.sofia.uni.fmi.dp.mobile.cli.command.commands.AddAdvertisementCommand;
import bg.sofia.uni.fmi.dp.mobile.cli.command.commands.SearchCommand;
import bg.sofia.uni.fmi.dp.mobile.filter.primitive.ExactValueFilter;
import bg.sofia.uni.fmi.dp.mobile.filter.Filter;
import bg.sofia.uni.fmi.dp.mobile.filter.primitive.RangeFilter;
import bg.sofia.uni.fmi.dp.mobile.notification.SubscriptionRule;
import bg.sofia.uni.fmi.dp.mobile.notification.notifier.EmailNotifier;
import bg.sofia.uni.fmi.dp.mobile.notification.notifier.PigeonNotifier;
import bg.sofia.uni.fmi.dp.mobile.notification.notifier.SmsNotifier;
import bg.sofia.uni.fmi.dp.mobile.notification.publisher.AdvertisementPublisher;
import bg.sofia.uni.fmi.dp.mobile.notification.subscriber.AdvertisementSubscriber;
import bg.sofia.uni.fmi.dp.mobile.notification.subscriber.EmailAdvertisementSubscriber;
import bg.sofia.uni.fmi.dp.mobile.notification.subscriber.PigeonAdvertisementSubscriber;
import bg.sofia.uni.fmi.dp.mobile.notification.subscriber.SmsAdvertisementSubscriber;
import bg.sofia.uni.fmi.dp.mobile.parser.QueryParser;
import bg.sofia.uni.fmi.dp.mobile.parser.RPNSearcher;
import bg.sofia.uni.fmi.dp.mobile.parser.Searcher;
import bg.sofia.uni.fmi.dp.mobile.vehicle.Vehicle;
import bg.sofia.uni.fmi.dp.mobile.vehicle.VehicleType;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final PrintStream PRINTER = System.out;
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        AdvertisementRepository advertisementRepository = new InMemoryAdRepository();
        Searcher searcher = new RPNSearcher(new QueryParser());
        AdvertisementService advertisementService = new AdvertisementService(advertisementRepository, searcher);

        CommandRegistry commandRegistry = new CommandRegistry();
        commandRegistry.addCommand(new AddAdvertisementCommand(advertisementService, SCANNER, PRINTER));
        commandRegistry.addCommand(new SearchCommand(advertisementService, SCANNER, PRINTER));

        CLI cli = new CLI(commandRegistry);
        cli.start();
    }
//    public static void main(String[] args) {
//        AdvertisementService service = getAdvertisementService();
//
//        // Създаване на нова обява
//        Advertisement ad = new Advertisement("audiA4", 15000,
//                new Vehicle(VehicleType.CAR, "Audi", "A4", 2010).addAttribute("engineType", "Diesel"),
//                "Mn qko audi", "Sofia");
//        service.addAdvertisement(ad);
//
//        Filter<Advertisement> priceFilter = new RangeFilter<>(Advertisement::price, 10000d, 20000d);
//
//        List<Advertisement> searchResults = service.searchAdvertisements(List.of(priceFilter));
//
//        // Принтиране на резултатите
//        searchResults.forEach(System.out::println);
//    }
//
//    private static AdvertisementService getAdvertisementService() {
//        AdvertisementRepository repository = new InMemoryAdRepository();
//        AdvertisementPublisher publisher = getPublisher();
//
//        // Създаване на филтри за SubscriptionRule
//        Filter<Advertisement> brandFilter = new ExactValueFilter<>(a -> a.vehicle().brand(), "Audi");
//        Filter<Advertisement> yearFilter = new RangeFilter<>(a -> a.vehicle().year(), 2005, 2015);
//
//        // Създаване на правила за абонамент
//        List<SubscriptionRule> subscriptionRules = List.of(new SubscriptionRule(List.of(brandFilter, yearFilter)));
//
//        // Инициализация на AdvertisementService
//        return new AdvertisementService(repository, publisher, subscriptionRules);
//    }
//
//    private static AdvertisementPublisher getPublisher() {
//        AdvertisementPublisher publisher = new AdvertisementPublisher();
//
//        // Създаване на subscriber-и
//        AdvertisementSubscriber emailSubscriber = new EmailAdvertisementSubscriber(new EmailNotifier(), "user@example.com");
//        AdvertisementSubscriber smsSubscriber = new SmsAdvertisementSubscriber(new SmsNotifier(), "123456789");
//        AdvertisementSubscriber pigeonSubscriber = new PigeonAdvertisementSubscriber(new PigeonNotifier()   , "address", 1234);
//
//        // Регистрация на subscriber-и
//        publisher.subscribe(emailSubscriber);
//        publisher.subscribe(smsSubscriber);
//        publisher.subscribe(pigeonSubscriber);
//        return publisher;
//    }
}
