package bg.sofia.uni.fmi.dp.mobile.filter.primitive.comparable;

@FunctionalInterface
public interface ComparisonOperation<V extends Comparable<V>> {
    boolean compare(V value1, V value2);
}