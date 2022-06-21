package jp.satomaru.util.component.element;

import java.time.LocalDateTime;
import java.util.Objects;

import jp.satomaru.util.component.ComponentException;
import jp.satomaru.util.component.ComponentId;
import jp.satomaru.util.component.element.mapper.ElementMapper;

/**
 * ローカル日時を保持するエレメントです。
 *
 * @author Satomaru
 */
public record LocalDateTimeElement(ComponentId id, LocalDateTime value) implements Element<LocalDateTime> {

	public LocalDateTimeElement {
		Objects.requireNonNull(id, "id");
		Objects.requireNonNull(value, "value");
	}

	@Override
	public <T> Element<T> map(ElementMapper<T> mapper) throws ComponentException {
		return mapper.map(this);
	}
}
