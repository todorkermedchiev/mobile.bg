package bg.sofia.uni.fmi.dp.mobile.parser;

import bg.sofia.uni.fmi.dp.mobile.advertisement.Advertisement;
import bg.sofia.uni.fmi.dp.mobile.vehicle.Vehicle;
import bg.sofia.uni.fmi.dp.mobile.vehicle.VehicleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RPNSearcherTest {
    private static final String ENGINE_TYPE = "engineType";

    private static final Advertisement BMW_X5 = new Advertisement("1", 10000,
            new Vehicle(VehicleType.CAR, "bmw", "x5", 2000).addAttribute(ENGINE_TYPE, "petrol"),
            "good condition", "sofia", LocalDateTime.now());

    private static final Advertisement BMW_E60 = new Advertisement("2", 7000,
            new Vehicle(VehicleType.CAR, "bmw", "e60", 2005).addAttribute(ENGINE_TYPE, "diesel"),
            "good condition", "sofia", LocalDateTime.now());

    private static final Advertisement A4_OLD = new Advertisement("3", 5000,
            new Vehicle(VehicleType.CAR, "audi", "a4", 2001).addAttribute(ENGINE_TYPE, "petrol"),
            "good condition", "sofia", LocalDateTime.now());

    private static final Advertisement A4_NEW = new Advertisement("4", 5000,
            new Vehicle(VehicleType.CAR, "audi", "a4", 2003).addAttribute(ENGINE_TYPE, "diesel"),
            "good condition", "sofia", LocalDateTime.now());

    private static final List<Advertisement> EXAMPLE_ADS = List.of(BMW_X5, BMW_E60, A4_OLD, A4_NEW);

    private RPNSearcher searcher;

    @BeforeEach
    void setUp() {
        RPNQueryParser rpnQueryParser = new RPNQueryParser();
        QueryFilterCreator queryFilterCreator = new RPNQueryFilterCreator(rpnQueryParser);
        searcher = new RPNSearcher(queryFilterCreator);
    }

    static Stream<Arguments> queryProvider() {
        return Stream.of(
                Arguments.of("brand = 'bmw'", List.of(BMW_X5, BMW_E60)),
                Arguments.of("year < 2002", List.of(BMW_X5, A4_OLD)),
                Arguments.of("year = 2005", List.of(BMW_E60)),
                Arguments.of("brand = 'bmw' | model = 'a4'", List.of(BMW_X5, BMW_E60, A4_OLD, A4_NEW)),
                Arguments.of("( brand = 'bmw' & model = 'x5' ) | ( brand = 'audi' & model = 'a4' & year > 2002 )",
                        List.of(BMW_X5, A4_NEW))
        );
    }

    @ParameterizedTest
    @MethodSource("queryProvider")
    void testQuery(String query, List<Advertisement> expectedResults) {
        List<Advertisement> actualResults = searcher.search(EXAMPLE_ADS, query);
        assertEquals(expectedResults, actualResults, "Query failed: " + query);
    }
}
