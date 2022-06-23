package jp.satomaru.util.function;

@FunctionalInterface
public interface TwoDimOperator<T> {

	T apply(int x, int y, T value);
}
