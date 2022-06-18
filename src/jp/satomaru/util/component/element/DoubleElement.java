package jp.satomaru.util.component.element;

import java.util.Objects;

import jp.satomaru.util.component.element.parser.ElementParser;

/**
 * 倍精度浮動小数点数を保持するエレメントです。
 *
 * @author Satomaru
 */
public record DoubleElement(Object id, Double value) implements Element<Double> {

	public DoubleElement {
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
