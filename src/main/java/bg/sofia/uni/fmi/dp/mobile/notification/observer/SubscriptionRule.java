package bg.sofia.uni.fmi.dp.mobile.notification.observer;

import bg.sofia.uni.fmi.dp.mobile.advertisement.Advertisement;
import bg.sofia.uni.fmi.dp.mobile.filter.Filter;

import java.util.List;

public class SubscriptionRule {
    private final List<Filter<Advertisement>> filters;

    public SubscriptionRule(List<Filter<Advertisement>> filters) {
        this.filters = filters;
    }

    public boolean matches(Advertisement ad) {
        return filters.stream().allMatch(filter -> filter.matches(ad));
    }
}