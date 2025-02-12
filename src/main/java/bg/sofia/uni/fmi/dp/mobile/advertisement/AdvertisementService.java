package bg.sofia.uni.fmi.dp.mobile.advertisement;

import bg.sofia.uni.fmi.dp.mobile.filter.Filter;
import bg.sofia.uni.fmi.dp.mobile.notification.NotificationRule;
import bg.sofia.uni.fmi.dp.mobile.notification.NotificationService;
import bg.sofia.uni.fmi.dp.mobile.parser.Searcher;

import java.time.Year;
import java.util.List;
import java.util.Map;

public class AdvertisementService {
    private final AdvertisementRepository repository;
    private final Searcher searcher;
    private final NotificationService notificationService;

    public AdvertisementService(AdvertisementRepository repository, Searcher searcher, NotificationService notificationService) {
        this.repository = repository;
        this.searcher = searcher;
        this.notificationService = notificationService;
    }

    public void addAdvertisement(Advertisement advertisement) {
        repository.save(advertisement);
        notificationService.onNewAdvertisementAdded(advertisement);
    }

    public void removeAdvertisement(String title) {
        repository.delete(title);
    }

    public Advertisement getAdvertisement(String title) {
        return repository.findByTitle(title);
    }

    public List<Advertisement> getAllAdvertisements() {
        return repository.findAll();
    }

    public List<Advertisement> searchAdvertisements(String query) {
        return searcher.search(repository.findAll(), query);
    }

    public void subscribe(NotificationRule notificationRule) {
        notificationService.subscribe(notificationRule);
    }

    public Map<Year, Double> getPriceStats(List<Filter<Advertisement>> filters) {
        return repository.getPriceStats(filters);
    }
}
