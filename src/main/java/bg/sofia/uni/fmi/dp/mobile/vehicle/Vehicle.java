package bg.sofia.uni.fmi.dp.mobile.vehicle;

import java.util.HashMap;
import java.util.Map;

public class Vehicle {
    private final VehicleType type;
    private final String brand;
    private final String model;
    private final Integer year;

    private final Map<String, String> attributes;

    public Vehicle(VehicleType type, String brand, String model, Integer year) {
        this(type, brand, model, year, new HashMap<>());
    }

    public Vehicle(VehicleType type, String brand, String model, Integer year, Map<String, String> attributes) {
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.attributes = attributes;
    }

    public VehicleType type() {
        return type;
    }

    public String brand() {
        return brand;
    }

    public String model() {
        return model;
    }

    public Integer year() {
        return year;
    }

    public Map<String, String> parameters() {
        return attributes;
    }

    public Vehicle addAttribute(String name, String value) {
        attributes.put(name, value);
        return this;
    }

    public String getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "type=" + type +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", attributes=" + attributes +
                '}';
    }
}
