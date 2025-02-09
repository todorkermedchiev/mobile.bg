package bg.sofia.uni.fmi.dp.mobile.cli.command.commands;

import bg.sofia.uni.fmi.dp.mobile.vehicle.Vehicle;
import bg.sofia.uni.fmi.dp.mobile.vehicle.VehicleBuilder;
import bg.sofia.uni.fmi.dp.mobile.vehicle.VehicleType;

import java.io.PrintStream;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class VehicleCreator {
    private VehicleBuilder builder;
    private PrintStream printer;
    private Scanner scanner;

    private final List<CommandStep> steps = List.of(
            () -> {
                printer.println("Enter vehicle type. Available types:");
                Arrays.stream(VehicleType.values()).forEach(v -> printer.println(v));
                String type = scanner.nextLine();
                builder.setType(VehicleType.valueOf(VehicleType.class, type));
            },
            () -> {
                printer.println("Enter vehicle brand:");
                String brand = scanner.nextLine();
                builder.setBrand(brand);
            },
            () -> {
                printer.println("Enter vehicle model:");
                String model = scanner.nextLine();
                builder.setModel(model);
            },
            () -> {
                printer.println("Enter vehicle make year:");
                Integer year = null;
                while (year == null || year <= 0 || year > Year.now().getValue()) {
                    try {
                        year = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        printer.println("Invalid year. Enter again:");
                    }
                }
                builder.setYear(year);
            },
            () -> {
                printer.println("Do you want to add vehicle attributes?");
                String reply = scanner.nextLine();

                while (reply.equalsIgnoreCase("yes")) {
                    printer.println("Enter attribute name:");
                    String name = scanner.nextLine();
                    printer.println("Enter attribute value:");
                    String value = scanner.nextLine();
                    builder.addAttribute(name, value);

                    printer.println("Do you want to add more attributes?");
                    reply = scanner.nextLine();
                }
            }
    );

    public VehicleCreator(PrintStream printer, Scanner scanner) {
        this(new VehicleBuilder(), printer, scanner);
    }

    public VehicleCreator(VehicleBuilder builder, PrintStream printer, Scanner scanner) {
        this.builder = builder;
        this.printer = printer;
        this.scanner = scanner;
    }

    public Vehicle create() {
        steps.forEach(CommandStep::execute);
        return builder.build();
    }

    public static void main(String[] args) { // todo remove
        VehicleCreator vehicleCreator = new VehicleCreator(System.out, new Scanner(System.in));
        System.out.println(vehicleCreator.create());
    }
}
