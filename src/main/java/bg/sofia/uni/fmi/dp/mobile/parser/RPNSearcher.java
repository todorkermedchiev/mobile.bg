package bg.sofia.uni.fmi.dp.mobile.parser;

import bg.sofia.uni.fmi.dp.mobile.advertisement.Advertisement;
import bg.sofia.uni.fmi.dp.mobile.filter.FieldExtractor;
import bg.sofia.uni.fmi.dp.mobile.filter.Filter;
import bg.sofia.uni.fmi.dp.mobile.filter.composite.AndFilter;
import bg.sofia.uni.fmi.dp.mobile.filter.composite.OrFilter;
import bg.sofia.uni.fmi.dp.mobile.filter.primitive.CaseInsensitiveFilter;
import bg.sofia.uni.fmi.dp.mobile.filter.primitive.comparable.CompareFilter;
import bg.sofia.uni.fmi.dp.mobile.filter.primitive.comparable.ComparisonOperator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Set;

public class RPNSearcher implements Searcher {
    private static final Set<String> ALLOWED_OPERATORS = Set.of("=", "<", ">", "<=", ">=", "|", "&");

    private final QueryParser queryParser;

    public RPNSearcher(QueryParser queryParser) {
        this.queryParser = queryParser;
    }

    @Override
    public List<Advertisement> search(List<Advertisement> advertisements, String query) {
        List<String> rpnQuery = queryParser.toPolishNotation(query);
        Deque<Filter<Advertisement>> filtersStack = new ArrayDeque<>();
        Deque<String> tokensStack = new ArrayDeque<>();


        for (String token : rpnQuery) {
            token = token.replaceAll("^\"|\"$|^'|'$", "");

            if (ALLOWED_OPERATORS.contains(token)) {
                Filter<Advertisement> filter = switch (token) {
                    case "=", "<", ">", "<=", ">=" -> {
                        String value = tokensStack.pop();
                        String operand = tokensStack.pop();
                        yield getPrimitiveFilterType(operand, value, token);
                    }
                    case "|" -> {
                        Filter<Advertisement> right = filtersStack.pop();
                        Filter<Advertisement> left = filtersStack.pop();
                        yield new OrFilter<>(left, right);
                    }
                    case "&" -> {
                        Filter<Advertisement> right = filtersStack.pop();
                        Filter<Advertisement> left = filtersStack.pop();
                        yield new AndFilter<>(left, right);
                    }
                    default -> throw new IllegalArgumentException("Invalid operator: " + token);
                };
                filtersStack.push(filter);
            } else {
                tokensStack.push(token);
            }
        }

        if (filtersStack.size() != 1) {
            throw new IllegalArgumentException("Invalid query: " + query);
        }

        return advertisements.stream()
            .filter(filtersStack.pop()::matches)
            .toList();
    }

    private Filter<Advertisement> getPrimitiveFilterType(String operand, String value, String operator) {
        FieldExtractor<Advertisement, ?> fieldExtractor = determineFieldExtractor(operand);
        Object parsedValue = parseValue(value);

        return createFilter(fieldExtractor, parsedValue, operator);
    }

    private FieldExtractor<Advertisement, ?> determineFieldExtractor(String operand) {
        return switch (operand) {
            case "price" -> Advertisement::price;
            case "brand" -> a -> a.vehicle().brand();
            case "model" -> a -> a.vehicle().model();
            case "year" -> a -> a.vehicle().year();
            default -> a -> a.vehicle().getAttribute(operand);
        };
    }

    private Object parseValue(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ignored) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException ignored2) {
                return value; // Връщаме като String, ако не може да се парсне като число.
            }
        }
    }

    private Filter<Advertisement> createFilter(FieldExtractor<Advertisement, ?> fieldExtractor, Object value, String operator) {
        if (value instanceof Integer intValue) {
            return new CompareFilter<>((FieldExtractor<Advertisement, Integer>) fieldExtractor, intValue, ComparisonOperator.fromString(operator));
        } else if (value instanceof Double doubleValue) {
            return new CompareFilter<>((FieldExtractor<Advertisement, Double>) fieldExtractor, doubleValue, ComparisonOperator.fromString(operator));
        } else {
            return new CaseInsensitiveFilter<>((FieldExtractor<Advertisement, String>) fieldExtractor, (String) value);
        }
    }


//    private Filter<Advertisement> getPrimitiveFilterType(String operand, String value, String operator) {
//        try {
//            int intValue = Integer.parseInt(value);
//            FieldExtractor<Advertisement, Integer> fieldExtractor = switch (operand) {
//                case "year" -> a -> a.vehicle().year();
//                default -> a -> Integer.parseInt(a.vehicle().getAttribute(operand));
//            };
//            return new CompareFilter<>(fieldExtractor, intValue, ComparisonOperator.fromString(operator));
//        } catch (NumberFormatException intParseException) {
//            try {
//                double doubleValue = Double.parseDouble(value);
//                FieldExtractor<Advertisement, Double> fieldExtractor = switch (operand) {
//                    case "price" -> Advertisement::price;
//                    default -> a -> Double.parseDouble(a.vehicle().getAttribute(operand));
//                };
//                return new CompareFilter<>(fieldExtractor, doubleValue, ComparisonOperator.fromString(operator));
//            } catch (NumberFormatException doubleParseException) {
//                FieldExtractor<Advertisement, String> fieldExtractor = a -> a.vehicle().getAttribute(operand);
//                return new CaseInsensitiveFilter<>(fieldExtractor, value);
//            }
//        }
//    }
}
