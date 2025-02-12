package bg.sofia.uni.fmi.dp.mobile.parser;

import bg.sofia.uni.fmi.dp.mobile.advertisement.Advertisement;
import bg.sofia.uni.fmi.dp.mobile.filter.Filter;
import java.util.List;

public class RPNSearcher implements Searcher {
    private final QueryFilterCreator queryFilterCreator;

    public RPNSearcher(QueryFilterCreator queryFilterCreator) {
        this.queryFilterCreator = queryFilterCreator;
    }

    @Override
    public List<Advertisement> search(List<Advertisement> advertisements, String query) {
        Filter<Advertisement> parsedFilter = queryFilterCreator.create(query);

        return advertisements.stream()
            .filter(parsedFilter::matches)
            .toList();
    }
}
