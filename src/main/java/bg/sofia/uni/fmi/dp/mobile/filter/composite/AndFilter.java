package bg.sofia.uni.fmi.dp.mobile.filter.composite;

import bg.sofia.uni.fmi.dp.mobile.filter.Filter;

public class AndFilter<T> implements Filter<T> {
    private final Filter<T> left;
    private final Filter<T> right;

    public AndFilter(Filter<T> left, Filter<T> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean matches(T item) {
        return left.matches(item) && right.matches(item);
    }
}
