package jp.satomaru.util.component.element;

import java.util.Objects;

import jp.satomaru.util.component.ComponentException;
import jp.satomaru.util.component.ComponentId;
import jp.satomaru.util.component.element.mapper.ElementMapper;

/**
 * 倍精度浮動小数点数を保持するエレメントです。
 *
 * @author Satomaru
 */
public record DoubleElement(ComponentId id, Double value) implements Element<Double> {

	public DoubleElement {
		Objects.requireNonNull(id, "id");
		Objects.requireNonNull(value, "value");
	}

	@Override
	public <T> Element<T> map(ElementMapper<T> mapper) throws ComponentException {
		return mapper.map(this);
	}
}
