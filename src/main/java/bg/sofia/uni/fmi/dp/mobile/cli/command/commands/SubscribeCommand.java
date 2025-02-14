package bg.sofia.uni.fmi.dp.mobile.cli.command.commands;

import bg.sofia.uni.fmi.dp.mobile.advertisement.Advertisement;
import bg.sofia.uni.fmi.dp.mobile.advertisement.AdvertisementService;
import bg.sofia.uni.fmi.dp.mobile.cli.localization.LocalizationService;
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
    private static final String COMMAND_NAME_KEY = "command.subscribe.name";
    private static final String COMMAND_DESCRIPTION_KEY = "command.subscribe.description";

    private final AdvertisementService advertisementService;
    private final QueryFilterCreator queryFilterCreator;
    private final Scanner scanner;
    private final PrintStream printer;
    private final LocalizationService localization;

    public SubscribeCommand(
            AdvertisementService advertisementService,
            QueryFilterCreator queryFilterCreator,
            LocalizationService localization,
            Scanner scanner,
            PrintStream printer
    ) {
        this.advertisementService = advertisementService;
        this.queryFilterCreator = queryFilterCreator;
        this.scanner = scanner;
        this.printer = printer;
        this.localization = localization;
    }

    @Override
    public void execute() {
        printer.println(localization.getMessage("prompt.enter.notification.method"));
        Arrays.stream(SubscriberType.values()).forEach(t -> printer.println(t.getName()));

        SubscriberType subscriberType = null;
        while (subscriberType == null) {
            try {
                subscriberType = SubscriberType.getByName(scanner.nextLine());
            } catch (RuntimeException e) {
                printer.println(localization.getMessage("error.invalid.notification.method"));
            }
        }

        Map<String, String> params = new HashMap<>();
        for (String argument : subscriberType.getArguments()) {
            printer.printf(localization.getMessage("prompt.enter.value"), argument);
            params.put(argument, scanner.nextLine());
        }

        AdvertisementSubscriber subscriber = new AdvertisementSubscriberFactory().createSubscriber(subscriberType.getName(), params);

        printer.println(localization.getMessage("prompt.enter.notification.criteria"));
        String query = scanner.nextLine();
        Filter<Advertisement> filter = queryFilterCreator.create(query);

        NotificationRule notificationRule = new NotificationRule(List.of(filter), subscriber);
        advertisementService.subscribe(notificationRule);
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
