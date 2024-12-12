package bg.sofia.uni.fmi.dp.mobile.vehicle;

public interface Vehicle { // todo think of better way
    String brand();
    String model();
    Integer year();

    VehicleType getType();
}
