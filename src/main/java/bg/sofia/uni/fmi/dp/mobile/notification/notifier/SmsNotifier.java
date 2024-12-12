package bg.sofia.uni.fmi.dp.mobile.notification.notifier;

public class SmsNotifier {
    public void sendSms(String phoneNumber, String message) {
        System.out.println("Sending SMS to " + phoneNumber + " with message: " + message);
    }
}
