package bg.sofia.uni.fmi.dp.mobile.filter;

public interface FieldExtractor<T, V> {
    V extractValue(T item);
}
