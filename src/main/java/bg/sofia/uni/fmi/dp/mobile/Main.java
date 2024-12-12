package bg.sofia.uni.fmi.dp.mobile;

import bg.sofia.uni.fmi.dp.mobile.advertisement.Advertisement;
import bg.sofia.uni.fmi.dp.mobile.advertisement.AdvertisementRepository;
import bg.sofia.uni.fmi.dp.mobile.advertisement.AdvertisementService;
import bg.sofia.uni.fmi.dp.mobile.advertisement.InMemoryAdRepository;
import bg.sofia.uni.fmi.dp.mobile.filter.ExactValueFilter;
import bg.sofia.uni.fmi.dp.mobile.filter.Filter;
import bg.sofia.uni.fmi.dp.mobile.filter.RangeFilter;
import bg.sofia.uni.fmi.dp.mobile.notification.observer.SubscriptionRule;
import bg.sofia.uni.fmi.dp.mobile.notification.observer.publisher.AdvertisementPublisher;
import bg.sofia.uni.fmi.dp.mobile.notification.observer.subscriber.AdvertisementSubscriber;
import bg.sofia.uni.fmi.dp.mobile.notification.observer.subscriber.EmailAdvertisementSubscriber;
import bg.sofia.uni.fmi.dp.mobile.notification.observer.subscriber.PigeonAdvertisementSubscriber;
import bg.sofia.uni.fmi.dp.mobile.notification.observer.subscriber.SmsAdvertisementSubscriber;
import bg.sofia.uni.fmi.dp.mobile.vehicle.Car;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AdvertisementService service = getAdvertisementService();

        // Създаване на нова обява
        Advertisement ad = new Advertisement("audiA4", 15000, new Car("Audi", "A4", 2010, "Diesel"), "Mn qko audi", "Sofia");
        service.addAdvertisement(ad);

        Filter<Advertisement> priceFilter = new RangeFilter<>(Advertisement::price, 10000d, 20000d);

        List<Advertisement> searchResults = service.searchAdvertisements(List.of(priceFilter));

        // Принтиране на резултатите
        searchResults.forEach(System.out::println);
    }

    private static AdvertisementService getAdvertisementService() {
        AdvertisementRepository repository = new InMemoryAdRepository();
        AdvertisementPublisher publisher = getPublisher();

        // Създаване на филтри за SubscriptionRule
        Filter<Advertisement> brandFilter = new ExactValueFilter<>(a -> a.vehicle().brand(), "Audi");
        Filter<Advertisement> yearFilter = new RangeFilter<>(a -> a.vehicle().year(), 2005, 2015);

        // Създаване на правила за абонамент
        List<SubscriptionRule> subscriptionRules = List.of(new SubscriptionRule(List.of(brandFilter, yearFilter)));

        // Инициализация на AdvertisementService
        return new AdvertisementService(repository, publisher, subscriptionRules);
    }

    private static AdvertisementPublisher getPublisher() {
        AdvertisementPublisher publisher = new AdvertisementPublisher();

        // Създаване на subscriber-и
        AdvertisementSubscriber emailSubscriber = new EmailAdvertisementSubscriber("user@example.com", "title");
        AdvertisementSubscriber smsSubscriber = new SmsAdvertisementSubscriber("123456789");
        AdvertisementSubscriber pigeonSubscriber = new PigeonAdvertisementSubscriber("address", 1234);

        // Регистрация на subscriber-и
        publisher.subscribe(emailSubscriber);
        publisher.subscribe(smsSubscriber);
        publisher.subscribe(pigeonSubscriber);
        return publisher;
    }
}
