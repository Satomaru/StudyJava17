package jp.satomaru.util.component.element;

import java.time.Instant;
import java.util.Objects;

import jp.satomaru.util.component.ComponentException;
import jp.satomaru.util.component.ComponentId;
import jp.satomaru.util.component.element.mapper.ElementMapper;

/**
 * インスタントを保持するエレメントです。
 *
 * @author Satomaru
 */
public record InstantElement(ComponentId id, Instant value) implements Element<Instant> {

	public InstantElement {
		Objects.requireNonNull(id, "id");
		Objects.requireNonNull(value, "value");
	}

	@Override
	public <T> Element<T> map(ElementMapper<T> mapper) throws ComponentException {
		return mapper.map(this);
	}
}
