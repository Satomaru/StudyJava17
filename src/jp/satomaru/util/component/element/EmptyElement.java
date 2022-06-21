package jp.satomaru.util.component.element;

import java.util.Objects;

import jp.satomaru.util.component.ComponentException;
import jp.satomaru.util.component.ComponentId;
import jp.satomaru.util.component.element.mapper.ElementMapper;

/**
 * NULLを保持するエレメントです。
 *
 * @author Satomaru
 */
public record EmptyElement<V> (ComponentId id) implements Element<V> {

	public EmptyElement {
		Objects.requireNonNull(id, "id");
	}

	@Override
	public V value() {
		return null;
	}

	@Override
	public <T> Element<T> map(ElementMapper<T> mapper) throws ComponentException {
		return new EmptyElement<>(id);
	}
}
