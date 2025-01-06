package bg.sofia.uni.fmi.dp.mobile.notification.subscriber;

import bg.sofia.uni.fmi.dp.mobile.notification.notifier.SmsNotifier;

public class SmsAdvertisementSubscriber implements AdvertisementSubscriber {
    private final SmsNotifier notifier;
    private final String phoneNumber;

    public SmsAdvertisementSubscriber(SmsNotifier notifier, String phoneNumber) {
        this.notifier = notifier;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void notify(String title, String message) {
        notifier.sendSms(phoneNumber, title + System.lineSeparator() + message);
    }
}
