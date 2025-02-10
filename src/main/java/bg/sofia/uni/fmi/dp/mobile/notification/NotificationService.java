package bg.sofia.uni.fmi.dp.mobile.notification;

import bg.sofia.uni.fmi.dp.mobile.advertisement.Advertisement;
import bg.sofia.uni.fmi.dp.mobile.advertisement.AdvertisementService;
import bg.sofia.uni.fmi.dp.mobile.advertisement.InMemoryAdRepository;
import bg.sofia.uni.fmi.dp.mobile.filter.primitive.ExactValueFilter;
import bg.sofia.uni.fmi.dp.mobile.notification.notifier.SmsNotifier;
import bg.sofia.uni.fmi.dp.mobile.notification.subscriber.SmsAdvertisementSubscriber;
import bg.sofia.uni.fmi.dp.mobile.parser.RPNQueryFilterCreator;
import bg.sofia.uni.fmi.dp.mobile.parser.RPNQueryParser;
import bg.sofia.uni.fmi.dp.mobile.parser.RPNSearcher;
import bg.sofia.uni.fmi.dp.mobile.vehicle.Vehicle;
import bg.sofia.uni.fmi.dp.mobile.vehicle.VehicleType;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private final List<NotificationRule> notificationRules = new ArrayList<>();

    public void subscribe(NotificationRule notificationRule) {
        notificationRules.add(notificationRule);
    }

    public void onNewAdvertisementAdded(Advertisement advertisement) {
        for (NotificationRule notificationRule : notificationRules) {
            boolean allMatch = notificationRule.filters().stream().allMatch(filter -> filter.matches(advertisement));

            if (allMatch) {
                String message = advertisement.vehicle().brand() + " "
                        + advertisement.vehicle().model() +
                        " for " + advertisement.price();

                notificationRule.subscriber().notify(
                        "New car found for you!",
                        message
                );
            }
        }
    }

    public static void main(String[] args) { // todo remove
        NotificationService notificationService = new NotificationService();
        AdvertisementService advertisementService = new AdvertisementService(new InMemoryAdRepository(), new RPNSearcher(new RPNQueryFilterCreator(new RPNQueryParser())), notificationService);

        advertisementService.subscribe(new NotificationRule(
                List.of(new ExactValueFilter<>(a -> a.vehicle().brand(), "vw")),
                new SmsAdvertisementSubscriber(new SmsNotifier(), "0876111909")
        ));

        advertisementService.addAdvertisement(new Advertisement(
                "title",
                2000, new Vehicle(VehicleType.CAR, "vw", "passat", 2000),
                "description",
                "location"
        ));
    }
}
