package jp.satomaru.util.function;

@FunctionalInterface
public interface IndexedConsumer<T> {

	void accept(int index, T value);
}
