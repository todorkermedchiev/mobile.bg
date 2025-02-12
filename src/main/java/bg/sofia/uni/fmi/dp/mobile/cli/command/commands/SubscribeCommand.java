package bg.sofia.uni.fmi.dp.mobile.cli.command.commands;

import bg.sofia.uni.fmi.dp.mobile.advertisement.AdvertisementService;
import bg.sofia.uni.fmi.dp.mobile.notification.NotificationRule;
import bg.sofia.uni.fmi.dp.mobile.parser.QueryFilterCreator;

import java.io.PrintStream;
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
        NotificationRule notificationRule = new NotificationRuleCreator(queryFilterCreator, printer, scanner).create();
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
