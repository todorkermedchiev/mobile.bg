package bg.sofia.uni.fmi.dp.mobile.advertisement;

import bg.sofia.uni.fmi.dp.mobile.filter.primitive.ExactValueFilter;
import bg.sofia.uni.fmi.dp.mobile.filter.Filter;
import bg.sofia.uni.fmi.dp.mobile.vehicle.Vehicle;
import bg.sofia.uni.fmi.dp.mobile.vehicle.VehicleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Map;

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
        Advertisement ad = new Advertisement(title, 2000, car, "Description", "Location", LocalDateTime.now());
        repository.save(ad);

        assertEquals(1, repository.findAll().size(), "The advertisement is not added");
        assertEquals(ad, repository.findByTitle(title), "The advertisement is not found by its title");
    }

    @Test
    void testDeleteShouldRemoveTheAdvertisement() {
        String title = "firstAd";
        Vehicle car = new Vehicle(VehicleType.CAR, "brand", "model", 2000).addAttribute("engineType", "diesel");
        Advertisement ad = new Advertisement(title, 2000, car, "Description", "Location", LocalDateTime.now());
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
        Advertisement ad = new Advertisement(title, 2000, car, "Description", "Location", LocalDateTime.now());

        repository.save(ad);

        Filter<Advertisement> filter = new ExactValueFilter<>(Advertisement::title, "anotherTitle"); // maybe mock this
        List<Advertisement> matches = repository.filter(List.of(filter));

        assertTrue(matches.isEmpty(), "The method should return empty list when nothing matches the filter");
    }

    @Test
    void testFilterWhenOneMatches() {
        String firstAdTitle = "firstAd";
        Vehicle firstCar = new Vehicle(VehicleType.CAR, "brand1", "model1", 2000).addAttribute("engineType", "diesel");
        Advertisement firstAd = new Advertisement(firstAdTitle, 2000, firstCar, "Description", "Location", LocalDateTime.now());

        String secondAdTitle = "secondAd";
        Vehicle secondCar = new Vehicle(VehicleType.CAR, "brand2", "model2", 2000).addAttribute("engineType", "petrol");
        Advertisement secondAd = new Advertisement(secondAdTitle, 2000, secondCar, "Description", "Location", LocalDateTime.now());

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
        Advertisement firstAd = new Advertisement(firstAdTitle, 2000, firstCar, "Description", "Location", LocalDateTime.now());

        String secondAdTitle = "secondAd";
        Vehicle secondCar = new Vehicle(VehicleType.CAR, "brand2", "model2", 2000).addAttribute("engineType", "petrol");
        Advertisement secondAd = new Advertisement(secondAdTitle, 2000, secondCar, "Description", "Location", LocalDateTime.now());

        repository.save(firstAd);
        repository.save(secondAd);

        Filter<Advertisement> filter = new ExactValueFilter<>(Advertisement::location, "Location");
        List<Advertisement> matches = repository.filter(List.of(filter));

        assertEquals(2, matches.size(), "The method should return the advertisement when it matches the filter");
        assertTrue(matches.contains(firstAd));
        assertTrue(matches.contains(secondAd));
    }

    @Test
    void testGetPriceStats() {
        String title1 = "advertisement1";
        Vehicle vehicle1 = new Vehicle(VehicleType.CAR, "brand", "model", 2010).addAttribute("engineType", "diesel");
        Advertisement advertisement1 = new Advertisement(title1, 40000, vehicle1, "Description", "Location", LocalDateTime.of(2010, Month.JANUARY, 10, 10, 10, 10));

        String title2 = "advertisement2";
        Vehicle vehicle2 = new Vehicle(VehicleType.CAR, "brand", "model", 2010).addAttribute("engineType", "diesel");
        Advertisement advertisement2 = new Advertisement(title2, 30000, vehicle2, "Description", "Location", LocalDateTime.of(2011, Month.JANUARY, 10, 10, 10, 10));

        String title3 = "advertisement3";
        Vehicle vehicle3 = new Vehicle(VehicleType.CAR, "brand", "model", 2010).addAttribute("engineType", "diesel");
        Advertisement advertisement3 = new Advertisement(title3, 20000, vehicle3, "Description", "Location", LocalDateTime.of(2012, Month.JANUARY, 10, 10, 10, 10));

        String title4 = "advertisement4";
        Vehicle vehicle4 = new Vehicle(VehicleType.CAR, "brand", "model", 2010).addAttribute("engineType", "diesel");
        Advertisement advertisement4 = new Advertisement(title4, 10000, vehicle4, "Description", "Location", LocalDateTime.of(2012, Month.JANUARY, 10, 10, 10, 10));

        repository.save(advertisement1);
        repository.save(advertisement2);
        repository.save(advertisement3);
        repository.save(advertisement4);

        Map<Year, Double> actual = repository.getPriceStats(List.of(new ExactValueFilter<>(a -> a.vehicle().brand(), "brand")));
        Map<Year, Double> expected = Map.of(
                Year.of(2010), 40000d,
                Year.of(2011), 30000d,
                Year.of(2012), 15000d
        );

        assertEquals(expected.size(), actual.size(), "The price statistics does not contain all of the years, or contains more than expected");
        expected.forEach((key, value) -> {
            assertTrue(actual.containsKey(key), "Missing key: " + key);
            assertEquals(value, actual.get(key), 0.001, "Wrong value for key: " + key);
        });
    }
}
