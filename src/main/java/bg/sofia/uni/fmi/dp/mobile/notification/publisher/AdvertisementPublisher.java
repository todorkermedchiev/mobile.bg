package bg.sofia.uni.fmi.dp.mobile.notification.publisher;

import bg.sofia.uni.fmi.dp.mobile.notification.subscriber.AdvertisementSubscriber;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementPublisher {
    private final List<AdvertisementSubscriber> subscribers = new ArrayList<>();

    public void subscribe(AdvertisementSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(AdvertisementSubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void notifySubscribers(String title, String message) {
        for (AdvertisementSubscriber subscriber : subscribers) {
            subscriber.notify(title, message);
        }
    }
}
