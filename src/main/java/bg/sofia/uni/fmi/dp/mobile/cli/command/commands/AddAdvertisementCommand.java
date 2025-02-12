package bg.sofia.uni.fmi.dp.mobile.cli.command.commands;

import bg.sofia.uni.fmi.dp.mobile.advertisement.Advertisement;
import bg.sofia.uni.fmi.dp.mobile.advertisement.AdvertisementService;

import java.io.PrintStream;
import java.util.Scanner;

public class AddAdvertisementCommand implements Command {
    private static final String COMMAND_NAME = "add-advertisement";
    private static final String COMMAND_DESCRIPTION = "Add a new advertisement";

    private final AdvertisementService service;
    private final Scanner scanner;
    private final PrintStream printer;

    public AddAdvertisementCommand(AdvertisementService service, Scanner scanner, PrintStream printer) {
        this.service = service;
        this.scanner = scanner;
        this.printer = printer;
    }

    @Override
    public void execute() {
        Advertisement advertisement = new AdvertisementCreator(printer, scanner).create();

        service.addAdvertisement(advertisement);
        System.out.println("Advertisement added successfully!");
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

