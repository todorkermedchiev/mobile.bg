package bg.sofia.uni.fmi.dp.mobile.advertisement;

import java.util.List;

public class AdvertisementService {
    private final AdvertisementRepository repository;

    public AdvertisementService() {
        this(new InMemoryAdRepository());
    }

    public AdvertisementService(AdvertisementRepository repository) {
        this.repository = repository;
    }

    public void addAdvertisement(Advertisement advertisement) {
        repository.save(advertisement);
    }

    public void removeAdvertisement(String id) {
        repository.delete(id);
    }

    public Advertisement getAdvertisement(String id) {
        return repository.findById(id);
    }

    public List<Advertisement> getAllAdvertisements() {
        return repository.findAll();
    }
}
