package bg.sofia.uni.fmi.dp.mobile.cli.command.commands;

import bg.sofia.uni.fmi.dp.mobile.advertisement.Advertisement;
import bg.sofia.uni.fmi.dp.mobile.advertisement.AdvertisementService;
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

public class SubscribeCommand implements Command {
    private static final String COMMAND_NAME = "subscribe";
    private static final String COMMAND_DESCRIPTION = "subscribe to get notifications for new advertisements";

    private final AdvertisementService advertisementService;
    private final QueryFilterCreator queryFilterCreator;
    private final Scanner scanner;
    private final PrintStream printer;

    public SubscribeCommand(AdvertisementService advertisementService, QueryFilterCreator queryFilterCreator, Scanner scanner, PrintStream printer) {
        this.advertisementService = advertisementService;
        this.queryFilterCreator = queryFilterCreator;
        this.scanner = scanner;
        this.printer = printer;
    }

    @Override
    public void execute() {
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

        NotificationRule notificationRule = new NotificationRule(List.of(filter), advertisementSubscriber);
        advertisementService.subscribe(notificationRule);
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
