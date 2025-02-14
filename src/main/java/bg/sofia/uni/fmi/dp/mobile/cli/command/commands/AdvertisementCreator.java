package bg.sofia.uni.fmi.dp.mobile.cli.command.commands;

import bg.sofia.uni.fmi.dp.mobile.advertisement.Advertisement;
import bg.sofia.uni.fmi.dp.mobile.advertisement.AdvertisementBuilder;
import bg.sofia.uni.fmi.dp.mobile.cli.localization.LocalizationService;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class AdvertisementCreator {
    private AdvertisementBuilder builder;
    private PrintStream printer;
    private Scanner scanner;
    private LocalizationService localization;

    private final List<CommandStep> steps;

    public AdvertisementCreator(PrintStream printer, Scanner scanner, LocalizationService localization) {
        this(new AdvertisementBuilder(), localization, printer, scanner);
    }

    public AdvertisementCreator(AdvertisementBuilder builder, LocalizationService localization, PrintStream printer, Scanner scanner) {
        this.builder = builder;
        this.printer = printer;
        this.scanner = scanner;
        this.localization = localization;

        this.steps = List.of(
                () -> {
                    printer.println(localization.getMessage("prompt.enter.title"));
                    builder.setTitle(scanner.nextLine());
                },
                () -> builder.setVehicle(new VehicleCreator(printer, scanner, localization).create()),
                () -> {
                    printer.println(localization.getMessage("prompt.enter.price"));
                    Double price = null;
                    while (price == null) {
                        try {
                            price = Double.parseDouble(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            printer.println(localization.getMessage("error.invalid.price"));
                        }
                    }
                    builder.setPrice(price);
                },
                () -> {
                    printer.println(localization.getMessage("prompt.enter.location"));
                    builder.setLocation(scanner.nextLine());
                },
                () -> {
                    printer.println(localization.getMessage("prompt.enter.description"));
                    builder.setDescription(scanner.nextLine());
                }
        );
    }

    public Advertisement create() {
        steps.forEach(CommandStep::execute);
        builder.setCreatedAt(LocalDateTime.now());
        return builder.build();
    }
}

