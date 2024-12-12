package bg.sofia.uni.fmi.dp.mobile.filter;

public interface Filter<T> {
    boolean matches(T item);
}
