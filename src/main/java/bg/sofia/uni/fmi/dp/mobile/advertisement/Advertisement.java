package bg.sofia.uni.fmi.dp.mobile.advertisement;

import bg.sofia.uni.fmi.dp.mobile.vehicle.Vehicle;

public record Advertisement(
        String id, // todo improve
        double price,
        Vehicle vehicle,
        String description,
        String location
        // todo ...
) {
}
