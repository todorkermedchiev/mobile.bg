package bg.sofia.uni.fmi.dp.mobile.advertisement;

import bg.sofia.uni.fmi.dp.mobile.vehicle.Vehicle;

public record Advertisement(
        String title,
        double price,
        Vehicle vehicle,
        String description,
        String location
) {
    @Override
    public String toString() {
        return "Advertisement{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", vehicle=" + vehicle +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
