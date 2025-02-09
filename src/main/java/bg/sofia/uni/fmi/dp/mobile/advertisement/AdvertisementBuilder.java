package bg.sofia.uni.fmi.dp.mobile.advertisement;

import bg.sofia.uni.fmi.dp.mobile.vehicle.Vehicle;

import java.util.Objects;

public class AdvertisementBuilder {
    private String title;
    private Double price;
    private Vehicle vehicle;
    private String description;
    private String location;

    public AdvertisementBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public AdvertisementBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public AdvertisementBuilder setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public AdvertisementBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public AdvertisementBuilder setLocation(String location) {
        this.location = location;
        return this;
    }

    public Advertisement build() {
        return new Advertisement(
                Objects.requireNonNull(title),
                Objects.requireNonNull(price),
                Objects.requireNonNull(vehicle),
                Objects.requireNonNull(description),
                Objects.requireNonNull(location)
        );
    }
}
