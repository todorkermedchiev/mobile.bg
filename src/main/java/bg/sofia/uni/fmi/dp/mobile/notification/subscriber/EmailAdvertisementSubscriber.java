package bg.sofia.uni.fmi.dp.mobile.notification.subscriber;

import bg.sofia.uni.fmi.dp.mobile.notification.notifier.EmailNotifier;

public class EmailAdvertisementSubscriber implements AdvertisementSubscriber {
    private final EmailNotifier notifier;
    private final String email;

    public EmailAdvertisementSubscriber(EmailNotifier notifier, String email) {
        this.notifier = notifier;
        this.email = email;
    }

    @Override
    public void notify(String title, String message) {
        notifier.sendEmail(email, title, message);
    }
}
