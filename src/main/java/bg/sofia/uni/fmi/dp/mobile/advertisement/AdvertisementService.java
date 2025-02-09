package bg.sofia.uni.fmi.dp.mobile.advertisement;

import bg.sofia.uni.fmi.dp.mobile.parser.Searcher;

import java.util.List;

public class AdvertisementService {
    private final AdvertisementRepository repository;
    private final Searcher searcher;

    public AdvertisementService(AdvertisementRepository repository, Searcher searcher) {
        this.repository = repository;
        this.searcher = searcher;
    }

    public void addAdvertisement(Advertisement advertisement) {
        repository.save(advertisement);
    }

    public void removeAdvertisement(String title) {
        repository.delete(title);
    }

    public Advertisement getAdvertisement(String title) {
        return repository.findByTitle(title);
    }

    public List<Advertisement> searchAdvertisements(String query) {
        return searcher.search(repository.findAll(), query);
    }

    public List<Advertisement> getAllAdvertisements() {
        return repository.findAll();
    }
}
