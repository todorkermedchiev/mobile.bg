package bg.sofia.uni.fmi.dp.mobile.advertisement;

import bg.sofia.uni.fmi.dp.mobile.filter.Filter;
import bg.sofia.uni.fmi.dp.mobile.notification.SubscriptionRule;
import bg.sofia.uni.fmi.dp.mobile.notification.publisher.AdvertisementPublisher;

import java.util.List;

public class AdvertisementService {
    private final AdvertisementRepository repository;
//    private final AdvertisementPublisher publisher;
//    private final List<SubscriptionRule> subscriptionRules; // todo maybe not immutable

    public AdvertisementService(AdvertisementRepository repository, AdvertisementPublisher publisher, List<SubscriptionRule> subscriptionRules) {
        this.repository = repository;
//        this.publisher = publisher;
//        this.subscriptionRules = subscriptionRules; // todo maybe setter, methods for adding and removing or extract to another class
    }

    public void addAdvertisement(Advertisement advertisement) {
        repository.save(advertisement);

//        for (SubscriptionRule rule : subscriptionRules) {
//            if (rule.matches(advertisement)) {
//                String message = "New advertisement: " + advertisement.toString();
//                publisher.notifySubscribers(message);
//            }
//        }
    }

    public void removeAdvertisement(String title) {
        repository.delete(title);
    }

    public Advertisement getAdvertisement(String title) {
        return repository.findByTitle(title);
    }

    public List<Advertisement> searchAdvertisements(List<Filter<Advertisement>> filters) {
        return repository.filter(filters);
    }

    public List<Advertisement> getAllAdvertisements() {
        return repository.findAll();
    }
}
