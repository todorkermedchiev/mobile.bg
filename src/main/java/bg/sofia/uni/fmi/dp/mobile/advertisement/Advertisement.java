package bg.sofia.uni.fmi.dp.mobile.advertisement;

import bg.sofia.uni.fmi.dp.mobile.vehicle.Vehicle;

public record Advertisement(
        String title,
        double price,
        Vehicle vehicle,
        String description,
        String location
) {
}
