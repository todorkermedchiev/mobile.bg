package bg.sofia.uni.fmi.dp.mobile.notification;

import bg.sofia.uni.fmi.dp.mobile.advertisement.Advertisement;

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
}
