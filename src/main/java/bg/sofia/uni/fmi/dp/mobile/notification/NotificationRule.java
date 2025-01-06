package bg.sofia.uni.fmi.dp.mobile.notification;

import bg.sofia.uni.fmi.dp.mobile.advertisement.Advertisement;
import bg.sofia.uni.fmi.dp.mobile.filter.Filter;
import bg.sofia.uni.fmi.dp.mobile.notification.subscriber.AdvertisementSubscriber;

import java.util.List;

public record NotificationRule(
        List<Filter<Advertisement>> filters,
        AdvertisementSubscriber subscriber
) {}
