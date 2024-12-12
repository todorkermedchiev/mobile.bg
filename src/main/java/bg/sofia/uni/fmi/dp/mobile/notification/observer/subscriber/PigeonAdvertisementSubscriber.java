package bg.sofia.uni.fmi.dp.mobile.notification.observer.subscriber;

import bg.sofia.uni.fmi.dp.mobile.notification.notifier.PigeonNotifier;

public class PigeonAdvertisementSubscriber implements AdvertisementSubscriber {
    private static final PigeonNotifier NOTIFIER = new PigeonNotifier();

    private final String address;
    private final Integer pigeonNumber;

    public PigeonAdvertisementSubscriber(String address, Integer pigeonNumber) {
        this.address = address;
        this.pigeonNumber = pigeonNumber;
    }

    @Override
    public void notify(String message) {
        NOTIFIER.sendPigeon(address, pigeonNumber, message);
    }
}
