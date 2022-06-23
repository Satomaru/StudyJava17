package jp.satomaru.util.component;

@FunctionalInterface
public interface ElementParser<V> {

	V parse(String name) throws ComponentException;
}
