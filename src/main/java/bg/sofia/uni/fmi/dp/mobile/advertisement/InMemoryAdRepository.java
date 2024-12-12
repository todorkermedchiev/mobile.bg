package bg.sofia.uni.fmi.dp.mobile.advertisement;

import bg.sofia.uni.fmi.dp.mobile.filter.Filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryAdRepository implements AdvertisementRepository {
    private final Map<String, Advertisement> advertisements = new HashMap<>();

    @Override
    public void save(Advertisement advertisement) {
        advertisements.put(advertisement.id(), advertisement);
    }

    @Override
    public void delete(String id) {
        advertisements.remove(id);
    }

    @Override
    public Advertisement findById(String id) {
        return advertisements.get(id);
    }

    @Override
    public List<Advertisement> findAll() {
        return new ArrayList<>(advertisements.values());
    }

    @Override
    public List<Advertisement> filter(List<Filter<Advertisement>> filters) {
        return findAll().stream()
                .filter(car -> filters.stream().allMatch(filter -> filter.matches(car)))
                .toList();
    }
}