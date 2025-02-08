package bg.sofia.uni.fmi.dp.mobile.vehicle;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Vehicle {
    private final VehicleType type;
    private final String brand;
    private final String model;
    private final Integer year;

    private final Map<String, String> attributes;

    public Vehicle(VehicleType type, String brand, String model, Integer year) {
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.year = year;

        this.attributes = new HashMap<>();
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
        String value = attributes.get(name);
//        if (value == null) {
//            throw new RuntimeException(); // todo another exception type
//        }
        return value;
    }

    public Optional<Class<?>> getAttributeType(String name) {
        Object value = attributes.get(name);
        return value != null ? Optional.of(value.getClass()) : Optional.empty();
    }
}
