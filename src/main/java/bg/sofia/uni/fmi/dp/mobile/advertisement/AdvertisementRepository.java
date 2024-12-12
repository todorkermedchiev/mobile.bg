package bg.sofia.uni.fmi.dp.mobile.advertisement;

import bg.sofia.uni.fmi.dp.mobile.filter.Filter;

import java.util.List;

public interface AdvertisementRepository {
    void save(Advertisement advertisement);
    void delete(String id);
    Advertisement findById(String id);
    List<Advertisement> findAll();
    List<Advertisement> filter(List<Filter<Advertisement>> filter); // todo generic ???
}
