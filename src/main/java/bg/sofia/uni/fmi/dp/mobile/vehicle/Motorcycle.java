package bg.sofia.uni.fmi.dp.mobile.vehicle;

public record Motorcycle(
        String brand,
        String model,
        Integer year,
        String coolingType // todo enum airCooled, waterCooled, etc
) implements Vehicle {
    @Override
    public VehicleType getType() {
        return VehicleType.MOTORCYCLE;
    }
}
