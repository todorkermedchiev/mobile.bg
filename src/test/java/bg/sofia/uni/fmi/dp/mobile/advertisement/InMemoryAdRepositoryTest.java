package bg.sofia.uni.fmi.dp.mobile.advertisement;

import bg.sofia.uni.fmi.dp.mobile.filter.ExactValueFilter;
import bg.sofia.uni.fmi.dp.mobile.filter.Filter;
import bg.sofia.uni.fmi.dp.mobile.vehicle.Car;
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
        String adId = "firstAd";
        Car car = new Car("brand", "model", 2000, "diesel");
        Advertisement ad = new Advertisement(adId, 2000, car, "Description", "Location");
        repository.save(ad);

        assertEquals(1, repository.findAll().size(), "The advertisement is not added");
        assertEquals(ad, repository.findById(adId), "The advertisement is not found by ID");
    }

    @Test
    void testDeleteShouldRemoveTheAdvertisement() {
        String adId = "firstAd";
        Car car = new Car("brand", "model", 2000, "diesel");
        Advertisement ad = new Advertisement(adId, 2000, car, "Description", "Location");
        repository.save(ad);
        assertEquals(1, repository.findAll().size(), "The advertisement is not added");
        assertEquals(ad, repository.findById(adId), "The advertisement is not found by ID");

        repository.delete(adId);
        assertTrue(repository.findAll().isEmpty(), "The advertisement is not deleted");
        assertNull(repository.findById(adId));
    }

    @Test
    void testFilterWhenNothingMatches() {
        String adId = "firstAd";
        Car car = new Car("brand", "model", 2000, "diesel");
        Advertisement ad = new Advertisement(adId, 2000, car, "Description", "Location");

        repository.save(ad);

        Filter<Advertisement> filter = new ExactValueFilter<>(Advertisement::id, "anotherId"); // maybe mock this
        List<Advertisement> matches = repository.filter(List.of(filter));

        assertTrue(matches.isEmpty(), "The method should return empty list when nothing matches the filter");
    }

    @Test
    void testFilterWhenOneMatches() {
        String firstAdId = "firstAd";
        Car firstCar = new Car("brand1", "model1", 2000, "diesel");
        Advertisement firstAd = new Advertisement(firstAdId, 2000, firstCar, "Description", "Location");

        String secondAdId = "secondAd";
        Car secondCar = new Car("brand2", "model2", 2000, "petrol");
        Advertisement secondAd = new Advertisement(secondAdId, 2000, secondCar, "Description", "Location");

        repository.save(firstAd);
        repository.save(secondAd);

        Filter<Advertisement> filter = new ExactValueFilter<>(Advertisement::id, firstAdId);
        List<Advertisement> matches = repository.filter(List.of(filter));

        assertEquals(1, matches.size(), "The method should return the advertisement when it matches the filter");
        assertEquals(matches.getFirst(), firstAd, "The returned advertisement is not as expected");
    }

    @Test
    void testFilterWhenAllMatches() {
        String firstAdId = "firstAd";
        Car firstCar = new Car("brand1", "model1", 2000, "diesel");
        Advertisement firstAd = new Advertisement(firstAdId, 2000, firstCar, "Description", "Location");

        String secondAdId = "secondAd";
        Car secondCar = new Car("brand2", "model2", 2000, "petrol");
        Advertisement secondAd = new Advertisement(secondAdId, 2000, secondCar, "Description", "Location");

        repository.save(firstAd);
        repository.save(secondAd);

        Filter<Advertisement> filter = new ExactValueFilter<>(Advertisement::location, "Location");
        List<Advertisement> matches = repository.filter(List.of(filter));

        assertEquals(2, matches.size(), "The method should return the advertisement when it matches the filter");
        assertTrue(matches.contains(firstAd));
        assertTrue(matches.contains(secondAd));
    }
}
