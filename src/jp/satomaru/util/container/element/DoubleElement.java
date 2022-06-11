package jp.satomaru.util.container.element;

import java.util.Objects;

import jp.satomaru.util.container.element.parser.ElementParser;

/**
 * 倍精度浮動小数点数を保持するエレメントです。
 *
 * @author Satomaru
 */
public record DoubleElement(String id, Double value) implements Element<Double> {

	public DoubleElement {
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
