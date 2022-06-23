package jp.satomaru.util.component;

@FunctionalInterface
public interface ElementParserWithRename<V> {

	V parseAndRename(String name, String newName) throws ComponentException;
}
