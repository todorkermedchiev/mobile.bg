package bg.sofia.uni.fmi.dp.mobile.vehicle;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class VehicleBuilder {
    private VehicleType type;
    private String brand;
    private String model;
    private Integer year;

    private final Map<String, String> attributes = new HashMap<>();

    public VehicleBuilder setType(VehicleType type) {
        this.type = type;
        return this;
    }

    public VehicleBuilder setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public VehicleBuilder setModel(String model) {
        this.model = model;
        return this;
    }

    public VehicleBuilder setYear(Integer year) {
        this.year = year;
        return this;
    }

    public VehicleBuilder addAttribute(String name, String value) {
        attributes.put(name, value);
        return this;
    }

    public Vehicle build() {
        return new Vehicle(
                Objects.requireNonNull(type),
                Objects.requireNonNull(brand),
                Objects.requireNonNull(model),
                Objects.requireNonNull(year),
                attributes
        );
    }
}
