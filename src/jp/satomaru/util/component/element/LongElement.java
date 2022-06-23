package jp.satomaru.util.component.element;

import java.util.Objects;

import jp.satomaru.util.component.ComponentException;
import jp.satomaru.util.component.ComponentId;
import jp.satomaru.util.component.element.mapper.ElementMapper;

/**
 * 長整数を保持するエレメントです。
 *
 * @author Satomaru
 */
public record LongElement(ComponentId id, Long value) implements Element<Long> {

	public LongElement {
		Objects.requireNonNull(id, "id");
		Objects.requireNonNull(value, "value");
	}

	@Override
	public <T> Element<T> map(ElementMapper<T> mapper) throws ComponentException {
		return mapper.map(this);
	}

	@Override
	public LongElement rename(String newName) {
		return new LongElement(id.rename(newName), value);
	}
}
