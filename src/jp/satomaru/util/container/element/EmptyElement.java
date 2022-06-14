package jp.satomaru.util.container.element;

import java.util.Objects;

import jp.satomaru.util.container.element.parser.ElementParser;

/**
 * NULLを保持するエレメントです。
 *
 * @author Satomaru
 */
public record EmptyElement(Object id) implements Element<Object> {

	public EmptyElement {
		Objects.requireNonNull(id, "id");
	}

	@Override
	public Object value() {
		return null;
	}

	@Override
	public Element<?> map(ElementParser<?> parser) throws ElementException {
		return this;
	}

	@Override
	public <P> P parse(ElementParser<P> parser) throws ElementException {
		return null;
	}
}
