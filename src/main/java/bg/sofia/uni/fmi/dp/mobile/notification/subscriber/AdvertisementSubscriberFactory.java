package bg.sofia.uni.fmi.dp.mobile.notification.subscriber;

import bg.sofia.uni.fmi.dp.mobile.notification.notifier.EmailNotifier;
import bg.sofia.uni.fmi.dp.mobile.notification.notifier.PigeonNotifier;
import bg.sofia.uni.fmi.dp.mobile.notification.notifier.SmsNotifier;

import java.util.Map;

public class AdvertisementSubscriberFactory {
    public AdvertisementSubscriber createSubscriber(String type, Map<String, String> params) {
        return switch (type.toLowerCase()) {
            case "email" -> new EmailAdvertisementSubscriber(
                    new EmailNotifier(),
                    params.get("email")
            );
            case "sms" -> new SmsAdvertisementSubscriber(
                    new SmsNotifier(),
                    params.get("phoneNumber")
            );
            case "pigeon" -> new PigeonAdvertisementSubscriber(
                    new PigeonNotifier(),
                    params.get("address")
            );
            default -> throw new IllegalArgumentException("Unknown subscriber type: " + type);
        };
    }
}

