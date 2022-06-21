package jp.satomaru.util.function;

@FunctionalInterface
public interface IndexedComsumer<T> {

	void accept(int index, T value);
}
