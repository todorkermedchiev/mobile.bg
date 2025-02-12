package bg.sofia.uni.fmi.dp.mobile.advertisement;

import bg.sofia.uni.fmi.dp.mobile.vehicle.Vehicle;

import java.time.LocalDateTime;

public record Advertisement(
        String title,
        double price,
        Vehicle vehicle,
        String description,
        String location,
        LocalDateTime createdAt
) {
    @Override
    public String toString() {
        return "Advertisement{" +
                "createdAt=" + createdAt +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", vehicle=" + vehicle +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
