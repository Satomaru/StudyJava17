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
		Objects.requireNonNull(value, "value");
	}

	@Override
	public Element<?> accept(ElementParser<?, ?> parser) throws ElementException {
		return parser.parse(this);
	}

	@Override
	public <P, E extends Element<P>> P parse(ElementParser<P, E> parser) throws ElementException {
		return parser.parse(this).value();
	}
}
