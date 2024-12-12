package bg.sofia.uni.fmi.dp.mobile.notification.observer.publisher;

import bg.sofia.uni.fmi.dp.mobile.notification.observer.subscriber.AdvertisementSubscriber;

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

    public void notifySubscribers(String message) {
        for (AdvertisementSubscriber subscriber : subscribers) {
            subscriber.notify(message);
        }
    }
}
