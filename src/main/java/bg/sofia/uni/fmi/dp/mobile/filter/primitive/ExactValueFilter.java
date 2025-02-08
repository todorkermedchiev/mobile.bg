package bg.sofia.uni.fmi.dp.mobile.filter.primitive;

import bg.sofia.uni.fmi.dp.mobile.filter.FieldExtractor;
import bg.sofia.uni.fmi.dp.mobile.filter.Filter;

public class ExactValueFilter<T, V> implements Filter<T> {
    private final V valueToFind;
    private final FieldExtractor<T, V> fieldExtractor;

    public ExactValueFilter(FieldExtractor<T, V> fieldExtractor, V valueToFind) {
        this.valueToFind = valueToFind;
        this.fieldExtractor = fieldExtractor;
    }

    public boolean matches(T item) {
        V value = fieldExtractor.extractValue(item);
        return value.equals(valueToFind);
    }
}