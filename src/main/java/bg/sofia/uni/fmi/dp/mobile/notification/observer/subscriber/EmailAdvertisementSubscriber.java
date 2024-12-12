package bg.sofia.uni.fmi.dp.mobile.notification.observer.subscriber;

import bg.sofia.uni.fmi.dp.mobile.notification.notifier.EmailNotifier;

public class EmailAdvertisementSubscriber implements AdvertisementSubscriber {
    private static final EmailNotifier NOTIFIER = new EmailNotifier(); // todo maybe injectable

    private final String email;
    private final String title;

    public EmailAdvertisementSubscriber(String email, String title) {
        this.email = email;
        this.title = title;
    }

    @Override
    public void notify(String message) {
        NOTIFIER.sendEmail(email, title, message);
    }
}
