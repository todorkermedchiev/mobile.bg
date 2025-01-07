package bg.sofia.uni.fmi.dp.mobile.filter.primitive.comparable;

public enum ComparisonOperator {
//    EQUAL("=", (v1, v2) -> v1.compareTo(v2) == 0),
    LESS_THAN("<", (v1, v2) -> v1.compareTo(v2) < 0),
    GREATER_THAN(">", (v1, v2) -> v1.compareTo(v2) > 0),
    LESS_THAN_OR_EQUAL("<=", (v1, v2) -> v1.compareTo(v2) <= 0),
    GREATER_THAN_OR_EQUAL(">=", (v1, v2) -> v1.compareTo(v2) >= 0);

    private final String operator;
    private final ComparisonOperation<?> operation;

    <V extends Comparable<V>> ComparisonOperator(String operator, ComparisonOperation<V> operation) {
        this.operator = operator;
        this.operation = operation;
    }

    public String getOperator() {
        return operator;
    }

    @SuppressWarnings("unchecked")
    public <V extends Comparable<V>> ComparisonOperation<V> getOperation() {
        return (ComparisonOperation<V>) operation;
    }

    public static ComparisonOperator fromString(String operator) {
        for (ComparisonOperator comparisonOperator : ComparisonOperator.values()) {
            if (comparisonOperator.getOperator().equals(operator)) {
                return comparisonOperator;
            }
        }
        throw new IllegalArgumentException("Unknown operator: " + operator);
    }
}