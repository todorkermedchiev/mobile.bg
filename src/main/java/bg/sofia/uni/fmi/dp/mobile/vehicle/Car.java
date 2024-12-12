package bg.sofia.uni.fmi.dp.mobile.vehicle;

public record Car(
        String brand,
        String model,
        Integer year,
        String engineType // todo enum or smth else
        // todo ...
) implements Vehicle {
    @Override
    public VehicleType getType() {
        return VehicleType.CAR;
    }
}

//public class Car implements Vehicle {
//    private final String brand;
//    private final String model;
//    private final int year;
//    private final String engineType; // todo enum or smth else
//    // todo ...
//
//    public Car(String brand, String model, int year, String engineType) {
//        this.brand = brand;
//        this.model = model;
//        this.year = year;
//        this.engineType = engineType;
//    }
//
//    public String getBrand() {
//        return brand;
//    }
//
//    public String getModel() {
//        return model;
//    }
//
//    public int getYear() {
//        return year;
//    }
//
//    public String getEngineType() {
//        return engineType;
//    }
//
//    @Override
//    public VehicleType getType() {
//        return VehicleType.CAR;
//    }
//}
