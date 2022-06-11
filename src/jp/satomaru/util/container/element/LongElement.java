package jp.satomaru.util.container.element;

import java.util.Objects;

import jp.satomaru.util.container.element.parser.ElementParser;

/**
 * 長整数を保持するエレメントです。
 *
 * @author Satomaru
 */
public record LongElement(String id, Long value) implements Element<Long> {

	public LongElement {
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
