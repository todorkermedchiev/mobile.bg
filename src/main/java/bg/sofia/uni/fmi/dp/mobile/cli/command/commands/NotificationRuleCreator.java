package bg.sofia.uni.fmi.dp.mobile.cli.command.commands;

import bg.sofia.uni.fmi.dp.mobile.advertisement.Advertisement;
import bg.sofia.uni.fmi.dp.mobile.advertisement.AdvertisementBuilder;
import bg.sofia.uni.fmi.dp.mobile.filter.Filter;
import bg.sofia.uni.fmi.dp.mobile.notification.NotificationRule;
import bg.sofia.uni.fmi.dp.mobile.notification.subscriber.AdvertisementSubscriber;
import bg.sofia.uni.fmi.dp.mobile.notification.subscriber.AdvertisementSubscriberFactory;
import bg.sofia.uni.fmi.dp.mobile.notification.subscriber.SubscriberType;
import bg.sofia.uni.fmi.dp.mobile.parser.QueryFilterCreator;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class NotificationRuleCreator {
    private final QueryFilterCreator queryFilterCreator;
    private final PrintStream printer;
    private final Scanner scanner;

    public NotificationRuleCreator(QueryFilterCreator queryFilterCreator, PrintStream printer, Scanner scanner) {
        this.queryFilterCreator = queryFilterCreator;
        this.printer = printer;
        this.scanner = scanner;
    }

    public NotificationRule create() {
        printer.println("Enter the notification method. Available methods:");
        Arrays.stream(SubscriberType.values()).forEach(t -> printer.println(t.getName()));
        String subscriberType = scanner.nextLine();

        Map<String, String> params = new HashMap<>();
        for (String argument : SubscriberType.getByName(subscriberType).getArguments()) {
            printer.println("Enter value for " + argument + ":");
            String value = scanner.nextLine();
            params.put(argument, value);
        }

        AdvertisementSubscriber advertisementSubscriber = new AdvertisementSubscriberFactory().createSubscriber(subscriberType, params);

        printer.println("Enter notification criteria (query):");
        String query = scanner.nextLine();
        Filter<Advertisement> filter = queryFilterCreator.create(query);

        return new NotificationRule(List.of(filter), advertisementSubscriber);
    }
}
