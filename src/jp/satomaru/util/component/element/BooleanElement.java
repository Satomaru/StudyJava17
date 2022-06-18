package jp.satomaru.util.component.element;

import java.util.Objects;

import jp.satomaru.util.component.element.parser.ElementParser;

/**
 * ブーリアンを保持するエレメントです。
 *
 * @author Satomaru
 */
public record BooleanElement(Object id, Boolean value) implements Element<Boolean> {

	public BooleanElement {
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