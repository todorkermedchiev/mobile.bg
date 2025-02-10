package bg.sofia.uni.fmi.dp.mobile.notification.subscriber;

import java.util.List;

public enum SubscriberType {
    PIGEON("pigeon", List.of("address")),
    EMAIL("email", List.of("email")),
    SMS("sms", List.of("phoneNumber"));

    private final String name;
    private final List<String> arguments;

    SubscriberType(String name, List<String> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public static SubscriberType getByName(String name) {
        for (SubscriberType type : SubscriberType.values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }

        throw new RuntimeException("Unknown notification method: " + name);
    }
}
