package jp.satomaru.util.component;

import java.util.Map;

import jp.satomaru.util.component.element.Element;
import jp.satomaru.util.component.element.EmptyElement;
import jp.satomaru.util.component.element.mapper.ElementMapper;

public final class ElementSet {

	@FunctionalInterface
	public interface GetAndMap<V> {

		Element<V> map(String name) throws ComponentException;
	}

	@FunctionalInterface
	public interface RenameAndMap<V> {

		Element<V> map(String name, String newName) throws ComponentException;
	}

	private final Class<?> type;

	private final Map<String, Element<?>> elements;

	ElementSet(Class<?> type, Map<String, Element<?>> elements) {
		this.type = type;
		this.elements = elements;
	}

	public Element<?> get(String name) {
		if (elements.containsKey(name)) {
			return elements.get(name);
		}

		return new EmptyElement<>(new ComponentId(type, name));
	}

	public <V> GetAndMap<V> getAndMapBy(ElementMapper<V> mapper) {
		return name -> get(name).map(mapper);
	}

	public <V> RenameAndMap<V> renameAndMap(ElementMapper<V> mapper) {
		return (name, newName) -> get(name).rename(newName).map(mapper);
	}
}
