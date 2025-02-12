package bg.sofia.uni.fmi.dp.mobile.advertisement;

import bg.sofia.uni.fmi.dp.mobile.filter.Filter;

import java.time.Year;
import java.util.List;
import java.util.Map;

public interface AdvertisementRepository {
    void save(Advertisement advertisement);
    void delete(String id);
    Advertisement findByTitle(String title);
    List<Advertisement> findAll();
    List<Advertisement> filter(List<Filter<Advertisement>> filters);
    Map<Year, Double> getPriceStats(List<Filter<Advertisement>> filters);
}
