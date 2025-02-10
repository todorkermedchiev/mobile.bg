package bg.sofia.uni.fmi.dp.mobile.parser;

import bg.sofia.uni.fmi.dp.mobile.advertisement.Advertisement;
import bg.sofia.uni.fmi.dp.mobile.filter.Filter;

public interface QueryFilterCreator {
    Filter<Advertisement> create(String query);
}
