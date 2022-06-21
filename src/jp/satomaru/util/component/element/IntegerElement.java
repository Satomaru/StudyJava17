package jp.satomaru.util.component.element;

import java.util.Objects;

import jp.satomaru.util.component.ComponentException;
import jp.satomaru.util.component.ComponentId;
import jp.satomaru.util.component.element.mapper.ElementMapper;

/**
 * 整数を保持するエレメントです。
 *
 * @author Satomaru
 */
public record IntegerElement(ComponentId id, Integer value) implements Element<Integer> {

	public IntegerElement {
		Objects.requireNonNull(id, "id");
		Objects.requireNonNull(value, "value");
	}

	@Override
	public <T> Element<T> map(ElementMapper<T> mapper) throws ComponentException {
		return mapper.map(this);
	}
}
