package bg.sofia.uni.fmi.dp.mobile.advertisement;

import bg.sofia.uni.fmi.dp.mobile.filter.primitive.ExactValueFilter;
import bg.sofia.uni.fmi.dp.mobile.filter.Filter;
import bg.sofia.uni.fmi.dp.mobile.vehicle.Vehicle;
import bg.sofia.uni.fmi.dp.mobile.vehicle.VehicleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryAdRepositoryTest {
    private AdvertisementRepository repository;

    @BeforeEach
    void initRepo() {
        repository = new InMemoryAdRepository();
    }

    @Test
    void testFindAllShouldReturnEmptyList() {
        assertTrue(repository.findAll().isEmpty(), "The repository should be empty");
    }

    @Test
    void testSaveShouldAddTheAdvertisement() {
        String title = "firstAd";
        Vehicle car = new Vehicle(VehicleType.CAR, "brand", "model", 2000).addAttribute("engineType", "diesel");
        Advertisement ad = new Advertisement(title, 2000, car, "Description", "Location");
        repository.save(ad);

        assertEquals(1, repository.findAll().size(), "The advertisement is not added");
        assertEquals(ad, repository.findByTitle(title), "The advertisement is not found by its title");
    }

    @Test
    void testDeleteShouldRemoveTheAdvertisement() {
        String title = "firstAd";
        Vehicle car = new Vehicle(VehicleType.CAR, "brand", "model", 2000).addAttribute("engineType", "diesel");
        Advertisement ad = new Advertisement(title, 2000, car, "Description", "Location");
        repository.save(ad);
        assertEquals(1, repository.findAll().size(), "The advertisement is not added");
        assertEquals(ad, repository.findByTitle(title), "The advertisement is not found by its title");

        repository.delete(title);
        assertTrue(repository.findAll().isEmpty(), "The advertisement is not deleted");
        assertNull(repository.findByTitle(title));
    }

    @Test
    void testFilterWhenNothingMatches() {
        String title = "firstAd";
        Vehicle car = new Vehicle(VehicleType.CAR, "brand", "model", 2000).addAttribute("engineType", "diesel");
        Advertisement ad = new Advertisement(title, 2000, car, "Description", "Location");

        repository.save(ad);

        Filter<Advertisement> filter = new ExactValueFilter<>(Advertisement::title, "anotherTitle"); // maybe mock this
        List<Advertisement> matches = repository.filter(List.of(filter));

        assertTrue(matches.isEmpty(), "The method should return empty list when nothing matches the filter");
    }

    @Test
    void testFilterWhenOneMatches() {
        String firstAdTitle = "firstAd";
        Vehicle firstCar = new Vehicle(VehicleType.CAR, "brand1", "model1", 2000).addAttribute("engineType", "diesel");
        Advertisement firstAd = new Advertisement(firstAdTitle, 2000, firstCar, "Description", "Location");

        String secondAdTitle = "secondAd";
        Vehicle secondCar = new Vehicle(VehicleType.CAR, "brand2", "model2", 2000).addAttribute("engineType", "petrol");
        Advertisement secondAd = new Advertisement(secondAdTitle, 2000, secondCar, "Description", "Location");

        repository.save(firstAd);
        repository.save(secondAd);

        Filter<Advertisement> filter = new ExactValueFilter<>(Advertisement::title, firstAdTitle);
        List<Advertisement> matches = repository.filter(List.of(filter));

        assertEquals(1, matches.size(), "The method should return the advertisement when it matches the filter");
        assertEquals(matches.get(0), firstAd, "The returned advertisement is not as expected");
    }

    @Test
    void testFilterWhenAllMatches() {
        String firstAdTitle = "firstAd";
        Vehicle firstCar = new Vehicle(VehicleType.CAR, "brand1", "model1", 2000).addAttribute("engineType", "diesel");
        Advertisement firstAd = new Advertisement(firstAdTitle, 2000, firstCar, "Description", "Location");

        String secondAdTitle = "secondAd";
        Vehicle secondCar = new Vehicle(VehicleType.CAR, "brand2", "model2", 2000).addAttribute("engineType", "petrol");
        Advertisement secondAd = new Advertisement(secondAdTitle, 2000, secondCar, "Description", "Location");

        repository.save(firstAd);
        repository.save(secondAd);

        Filter<Advertisement> filter = new ExactValueFilter<>(Advertisement::location, "Location");
        List<Advertisement> matches = repository.filter(List.of(filter));

        assertEquals(2, matches.size(), "The method should return the advertisement when it matches the filter");
        assertTrue(matches.contains(firstAd));
        assertTrue(matches.contains(secondAd));
    }
}
