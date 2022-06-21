package jp.satomaru.util.function;

@FunctionalInterface
public interface TwoDimConsumer<T> {

	void accept(int x, int y, T value);
}
