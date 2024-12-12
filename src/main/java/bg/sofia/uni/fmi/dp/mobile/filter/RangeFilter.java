package bg.sofia.uni.fmi.dp.mobile.filter;

public class RangeFilter<T, V extends Comparable<V>> implements Filter<T> {
    private final FieldExtractor<T, V> fieldExtractor;
    private final V min;
    private final V max;

    public RangeFilter(FieldExtractor<T, V> fieldExtractor, V min, V max) {
        this.fieldExtractor = fieldExtractor;
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean matches(T item) {
        V value = fieldExtractor.extractValue(item);
        return value.compareTo(min) >= 0
                && value.compareTo(max) <= 0;
    }
}
