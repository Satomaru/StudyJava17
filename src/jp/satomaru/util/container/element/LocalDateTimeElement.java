package jp.satomaru.util.container.element;

import java.time.LocalDateTime;
import java.util.Objects;

import jp.satomaru.util.container.element.parser.ElementParser;

/**
 * ローカル日時を保持するエレメントです。
 *
 * @author Satomaru
 */
public record LocalDateTimeElement(String id, LocalDateTime value) implements Element<LocalDateTime> {

	public LocalDateTimeElement {
		Objects.requireNonNull(id, "id");
	}

	@Override
	public <E extends Element<?>> E accept(ElementParser<?, E> parser) throws ElementException {
		return parser.parse(this);
	}
}
