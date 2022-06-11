package jp.satomaru.util.container.element;

import java.util.Objects;

import jp.satomaru.util.container.element.parser.ElementParser;

/**
 * 整数を保持するエレメントです。
 *
 * @author Satomaru
 */
public record IntegerElement(String id, Integer value) implements Element<Integer> {

	public IntegerElement {
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
