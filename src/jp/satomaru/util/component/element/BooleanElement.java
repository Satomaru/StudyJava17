package jp.satomaru.util.component.element;

import java.util.Objects;

import jp.satomaru.util.component.ComponentException;
import jp.satomaru.util.component.ComponentId;
import jp.satomaru.util.component.element.mapper.ElementMapper;

/**
 * ブーリアンを保持するエレメントです。
 *
 * @author Satomaru
 */
public record BooleanElement(ComponentId id, Boolean value) implements Element<Boolean> {

	public BooleanElement {
		Objects.requireNonNull(id, "id");
		Objects.requireNonNull(value, "value");
	}

	@Override
	public <T> Element<T> map(ElementMapper<T> mapper) throws ComponentException {
		return mapper.map(this);
	}
}
