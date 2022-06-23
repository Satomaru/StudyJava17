package jp.satomaru.util.component;

import java.util.Map;

import jp.satomaru.util.component.element.Element;
import jp.satomaru.util.component.element.EmptyElement;
import jp.satomaru.util.component.element.mapper.ElementMapper;

public final class ElementSet {

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

	public <V> ElementParser<V> parseBy(ElementMapper<V> mapper) {
		return name -> get(name).parse(mapper);
	}

	public <V> ElementParser<V> parseOrThrowBy(ElementMapper<V> mapper) {
		return name -> get(name).parseOrThrow(mapper);
	}

	public <V> ElementParserWithRename<V> parseAndRenameBy(ElementMapper<V> mapper) {
		return (name, newName) -> get(name).rename(newName).parse(mapper);
	}

	public <V> ElementParserWithRename<V> parseAndRenameOrThrowBy(ElementMapper<V> mapper) {
		return (name, newName) -> get(name).rename(newName).parseOrThrow(mapper);
	}
}
