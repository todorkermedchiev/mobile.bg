package bg.sofia.uni.fmi.dp.mobile.cli.command.commands;

import bg.sofia.uni.fmi.dp.mobile.advertisement.Advertisement;
import bg.sofia.uni.fmi.dp.mobile.advertisement.AdvertisementBuilder;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class AdvertisementCreator {
    private AdvertisementBuilder builder;
    private PrintStream printer;
    private Scanner scanner;

    private final List<CommandStep> steps = List.of(
            () -> {
                printer.println("Enter advertisement title:"); // todo language support
                String title = scanner.nextLine();
                builder.setTitle(title);
            },
            () -> builder.setVehicle(new VehicleCreator(printer, scanner).create()),
            () -> {
                printer.println("Enter vehicle price:");
                Double price = null;
                while (price == null) {
                    try {
                        price = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        printer.println("Invalid price. Enter again:");
                    }
                }
                builder.setPrice(price);
            },
            () -> {
                printer.println("Enter vehicle location:");
                String location = scanner.nextLine();
                builder.setLocation(location);
            },
            () -> {
                printer.println("Enter vehicle description:");
                String description = scanner.nextLine();
                builder.setDescription(description);
            }
    );

    public AdvertisementCreator(PrintStream printer, Scanner scanner) {
        this(new AdvertisementBuilder(), printer, scanner);
    }

    public AdvertisementCreator(AdvertisementBuilder builder, PrintStream printer, Scanner scanner) {
        this.builder = builder;
        this.printer = printer;
        this.scanner = scanner;
    }

    public Advertisement create() {
        steps.forEach(CommandStep::execute);
        return builder.build();
    }

    public static void main(String[] args) {
        Advertisement advertisement = new AdvertisementCreator(System.out, new Scanner(System.in)).create();
    }
}
