package bg.sofia.uni.fmi.dp.mobile.notification.subscriber;

import bg.sofia.uni.fmi.dp.mobile.notification.notifier.PigeonNotifier;

public class PigeonAdvertisementSubscriber implements AdvertisementSubscriber {
    private final PigeonNotifier notifier;
    private final String address;

    public PigeonAdvertisementSubscriber(PigeonNotifier notifier, String address) {
        this.notifier = notifier;
        this.address = address;
    }

    @Override
    public void notify(String title, String message) {
        notifier.sendPigeon(address, title + System.lineSeparator() + message);
    }
}
