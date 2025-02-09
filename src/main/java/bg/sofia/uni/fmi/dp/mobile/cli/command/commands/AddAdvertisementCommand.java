package bg.sofia.uni.fmi.dp.mobile.cli.command.commands;

import bg.sofia.uni.fmi.dp.mobile.advertisement.Advertisement;
import bg.sofia.uni.fmi.dp.mobile.advertisement.AdvertisementRepository;
import bg.sofia.uni.fmi.dp.mobile.vehicle.Vehicle;
import bg.sofia.uni.fmi.dp.mobile.vehicle.VehicleType;

import java.util.Scanner;

public class AddAdvertisementCommand implements Command {
    private static final String COMMAND_NAME = "add-advertisement";

    private final AdvertisementRepository repository;

    public AddAdvertisementCommand(AdvertisementRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        // Advertisement creator
        // Advertisement advertisement = advCreator.create(); // maybe here pass list of steps and language, idk

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter brand: ");
        String brand = scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();
        System.out.print("Enter year: ");
        int year = Integer.parseInt(scanner.nextLine());

        Vehicle vehicle = new Vehicle(VehicleType.CAR, brand, model, year);
        Advertisement ad = new Advertisement(String.valueOf(System.currentTimeMillis()), 1000, vehicle, "New car", "Sofia");

        repository.save(ad);
        System.out.println("Advertisement added successfully!");
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Add a new advertisement";
    }
}

