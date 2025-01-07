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
        FieldExtractor<Advertisement, String> fieldExtractor = switch (operand) {
            case "brand" -> a -> a.vehicle().brand();
            case "model" -> a -> a.vehicle().model();
            case "year" -> a -> a.vehicle().year().toString();
            // todo add more fields or think of better way to extract fields by string
            default -> throw new IllegalArgumentException("Invalid operand: " + operand);
        };

        return switch (operator) {
            case "=" -> new CaseInsensitiveFilter<>(fieldExtractor, value);
            case "<", ">", ">=", "<=" -> new CompareFilter<>(fieldExtractor, value, ComparisonOperator.fromString(operator));
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }
}
