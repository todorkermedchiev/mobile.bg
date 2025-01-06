package bg.sofia.uni.fmi.dp.mobile.parser;

import bg.sofia.uni.fmi.dp.mobile.advertisement.Advertisement;

import java.util.List;

public interface Searcher {
    List<Advertisement> search(List<Advertisement> listings, String query);
}
