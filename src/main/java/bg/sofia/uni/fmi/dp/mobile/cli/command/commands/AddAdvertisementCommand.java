package bg.sofia.uni.fmi.dp.mobile.cli.command.commands;

import bg.sofia.uni.fmi.dp.mobile.advertisement.Advertisement;
import bg.sofia.uni.fmi.dp.mobile.advertisement.AdvertisementService;
import bg.sofia.uni.fmi.dp.mobile.cli.localization.LocalizationService;

import java.io.PrintStream;
import java.util.Scanner;

public class AddAdvertisementCommand implements Command {
    private final AdvertisementService service;
    private final Scanner scanner;
    private final PrintStream printer;
    private final LocalizationService localization;

    public AddAdvertisementCommand(AdvertisementService service, LocalizationService localization, Scanner scanner, PrintStream printer) {
        this.service = service;
        this.scanner = scanner;
        this.printer = printer;
        this.localization = localization;
    }

    @Override
    public void execute() {
        Advertisement advertisement = new AdvertisementCreator(printer, scanner, localization).create();

        service.addAdvertisement(advertisement);
        printer.println(localization.getMessage("message.advertisement.added"));
    }

    @Override
    public String getName() {
        return localization.getMessage("command.add.name");
    }

    @Override
    public String getDescription() {
        return localization.getMessage("command.add.description");
    }
}

