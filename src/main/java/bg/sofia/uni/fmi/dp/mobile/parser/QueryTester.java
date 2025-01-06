package bg.sofia.uni.fmi.dp.mobile.parser;

import bg.sofia.uni.fmi.dp.mobile.advertisement.Advertisement;
import bg.sofia.uni.fmi.dp.mobile.vehicle.Car;

import java.util.List;
import java.util.Map;

public class QueryTester {
    public static final Advertisement BMW_X5 = new Advertisement("1", 10000, new Car("bmw", "x5", 2000, "petrol"), "good condition", "sofia");
    public static final Advertisement BMW_E60 = new Advertisement("2", 7000, new Car("bmw", "e60", 2005, "diesel"), "good condition", "sofia");
    public static final Advertisement A4_OLD = new Advertisement("3", 5000, new Car("audi", "a4", 2001, "petrol"), "good condition", "sofia");
    public static final Advertisement A4_NEW = new Advertisement("4", 5000, new Car("audi", "a4", 2003, "diesel"), "good condition", "sofia");
    public final static List<Advertisement> EXAMPLE_ADS = List.of(
        BMW_X5,
        BMW_E60,
        A4_OLD,
        A4_NEW
    );

    public final static Map<String, List<Advertisement>> QUERY_TO_EXPECTED_RESULTS = Map.of(
        "brand = 'bmw'", List.of(BMW_X5, BMW_E60),
        "year < 2002", List.of(BMW_X5, A4_OLD),
        "year = 2005", List.of(BMW_E60),
        "brand = 'bmw' | model = 'a4'", List.of(BMW_X5, BMW_E60, A4_OLD, A4_NEW),
        "( brand = 'bmw' & model = 'x5' ) | ( brand = 'audi' & model = 'a4' & year > 2002 )", List.of(BMW_X5, A4_NEW)
    );

    public void test(Searcher searcher) {
        QUERY_TO_EXPECTED_RESULTS.forEach((query, expected) -> test(searcher, query, expected));
    }

    private void test(Searcher searcher, String query, List<Advertisement> expected) {
        System.out.println();
        try {
            List<Advertisement> result = searcher.search(EXAMPLE_ADS, query);
            if (result.equals(expected)) {
                System.out.println("Query: " + query + " passed :)");
            } else {
                System.out.println("Query: " + query + " failed :(");
                System.out.println("Expected: " + expected);
                System.out.println("Got: " + result);
            }
        } catch (Exception e) {
            System.out.println("Query: " + query + " failed with exception :(");
            System.out.println("Exception: " + e);
        }
    }
}
