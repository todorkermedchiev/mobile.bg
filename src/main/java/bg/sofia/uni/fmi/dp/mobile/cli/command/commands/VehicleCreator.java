package bg.sofia.uni.fmi.dp.mobile.cli.command.commands;

import bg.sofia.uni.fmi.dp.mobile.cli.localization.LocalizationService;
import bg.sofia.uni.fmi.dp.mobile.vehicle.Vehicle;
import bg.sofia.uni.fmi.dp.mobile.vehicle.VehicleBuilder;
import bg.sofia.uni.fmi.dp.mobile.vehicle.VehicleType;

import java.io.PrintStream;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class VehicleCreator {
    private final VehicleBuilder builder;
    private final PrintStream printer;
    private final Scanner scanner;
    private final LocalizationService localization;

    private final List<CommandStep> steps;

    public VehicleCreator(PrintStream printer, Scanner scanner, LocalizationService localization) {
        this(new VehicleBuilder(), localization, printer, scanner);
    }

    public VehicleCreator(VehicleBuilder builder, LocalizationService localization, PrintStream printer, Scanner scanner) {
        this.builder = builder;
        this.printer = printer;
        this.scanner = scanner;
        this.localization = localization;

        this.steps = List.of(
                this::askVehicleType,
                this::askBrand,
                this::askModel,
                this::askYear,
                this::askAttributes
        );
    }

    private void askVehicleType() {
        printer.println(localization.getMessage("prompt.enter.vehicle.type"));
        Arrays.stream(VehicleType.values()).forEach(printer::println);

        while (true) {
            try {
                String typeInput = scanner.nextLine().toUpperCase();
                builder.setType(VehicleType.valueOf(typeInput));
                break;
            } catch (IllegalArgumentException e) {
                printer.println(localization.getMessage("error.invalid.vehicle.type"));
            }
        }
    }

    private void askBrand() {
        printer.println(localization.getMessage("prompt.enter.vehicle.brand"));
        builder.setBrand(scanner.nextLine());
    }

    private void askModel() {
        printer.println(localization.getMessage("prompt.enter.vehicle.model"));
        builder.setModel(scanner.nextLine());
    }

    private void askYear() {
        printer.println(localization.getMessage("prompt.enter.vehicle.year"));
        Integer year = null;
        while (year == null || year <= 0 || year > Year.now().getValue()) {
            try {
                year = Integer.parseInt(scanner.nextLine());
                if (year > 0 && year <= Year.now().getValue()) {
                    builder.setYear(year);
                } else {
                    printer.println(localization.getMessage("error.invalid.year"));
                }
            } catch (NumberFormatException e) {
                printer.println(localization.getMessage("error.invalid.year"));
            }
        }
    }

    private void askAttributes() {
        printer.println(localization.getMessage("prompt.add.vehicle.attributes"));
        String reply = scanner.nextLine();

        while (reply.equalsIgnoreCase("yes")) {
            printer.println(localization.getMessage("prompt.enter.attribute.name"));
            String name = scanner.nextLine();
            printer.println(localization.getMessage("prompt.enter.attribute.value"));
            String value = scanner.nextLine();
            builder.addAttribute(name, value);

            printer.println(localization.getMessage("prompt.add.more.attributes"));
            reply = scanner.nextLine();
        }
    }

    public Vehicle create() {
        steps.forEach(CommandStep::execute);
        return builder.build();
    }
}

