package bg.sofia.uni.fmi.dp.mobile.filter.primitive.comparable;

import bg.sofia.uni.fmi.dp.mobile.filter.FieldExtractor;
import bg.sofia.uni.fmi.dp.mobile.filter.Filter;

public class CompareFilter<T, V extends Comparable<V>> implements Filter<T> {
    private final FieldExtractor<T, V> fieldExtractor;
    private final V value;
    private final ComparisonOperation<V> operation;

    public CompareFilter(FieldExtractor<T, V> fieldExtractor, V value, ComparisonOperator operator) {
        this.fieldExtractor = fieldExtractor;
        this.value = value;
        this.operation = operator.getOperation();
    }

    @Override
    public boolean matches(T item) {
        V extractedValue = fieldExtractor.extractValue(item);
        return operation.compare(extractedValue, value);
    }
}