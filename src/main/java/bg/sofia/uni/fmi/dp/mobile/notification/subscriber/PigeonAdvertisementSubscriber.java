package bg.sofia.uni.fmi.dp.mobile.notification.subscriber;

import bg.sofia.uni.fmi.dp.mobile.notification.notifier.PigeonNotifier;

public class PigeonAdvertisementSubscriber implements AdvertisementSubscriber {
    private final PigeonNotifier notifier;
    private final String address;
    private final Integer pigeonNumber;

    public PigeonAdvertisementSubscriber(PigeonNotifier notifier, String address, Integer pigeonNumber) {
        this.notifier = notifier;
        this.address = address;
        this.pigeonNumber = pigeonNumber; // todo maybe constant or other logic
    }

    @Override
    public void notify(String title, String message) {
        notifier.sendPigeon(address, pigeonNumber, title + System.lineSeparator() + message);
    }
}
