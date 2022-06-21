package jp.satomaru.util.function;

@FunctionalInterface
public interface TwoDimSupplier<T> {

	T get(int x, int y);
}
