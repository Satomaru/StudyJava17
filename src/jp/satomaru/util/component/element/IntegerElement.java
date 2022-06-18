package jp.satomaru.util.component.element;

import java.util.Objects;

import jp.satomaru.util.component.element.parser.ElementParser;

/**
 * 整数を保持するエレメントです。
 *
 * @author Satomaru
 */
public record IntegerElement(Object id, Integer value) implements Element<Integer> {

	public IntegerElement {
		Objects.requireNonNull(id, "id");
		Objects.requireNonNull(value, "value");
	}

	@Override
	public Element<?> map(ElementParser<?> parser) throws ElementException {
		return parser.parse(this);
	}

	@Override
	public <P> P parse(ElementParser<P> parser) throws ElementException {
		return parser.parse(this).value();
	}
}