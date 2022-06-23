package jp.satomaru.util.component.element;

import java.util.Objects;

import jp.satomaru.util.component.ComponentException;
import jp.satomaru.util.component.ComponentId;
import jp.satomaru.util.component.element.mapper.ElementMapper;

/**
 * 文字列を保持するエレメントです。
 *
 * @author Satomaru
 */
public record StringElement(ComponentId id, String value) implements Element<String> {

	public StringElement {
		Objects.requireNonNull(id, "id");
		Objects.requireNonNull(value, "value");
	}

	@Override
	public <T> Element<T> map(ElementMapper<T> mapper) throws ComponentException {
		return mapper.map(this);
	}

	@Override
	public StringElement rename(String newName) {
		return new StringElement(id.rename(newName), value);
	}
}
