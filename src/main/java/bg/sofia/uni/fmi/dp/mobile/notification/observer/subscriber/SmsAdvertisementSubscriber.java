package bg.sofia.uni.fmi.dp.mobile.notification.observer.subscriber;

import bg.sofia.uni.fmi.dp.mobile.notification.notifier.SmsNotifier;

public class SmsAdvertisementSubscriber implements AdvertisementSubscriber {
    private static final SmsNotifier NOTIFIER = new SmsNotifier();

    private final String phoneNumber;

    public SmsAdvertisementSubscriber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void notify(String message) {
        NOTIFIER.sendSms(phoneNumber, message);
    }
}
