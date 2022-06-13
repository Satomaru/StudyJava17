package jp.satomaru.util.container.element.parser;

import jp.satomaru.util.container.element.BooleanElement;
import jp.satomaru.util.container.element.DoubleElement;
import jp.satomaru.util.container.element.Element;
import jp.satomaru.util.container.element.ElementException;
import jp.satomaru.util.container.element.ElementException.Type;
import jp.satomaru.util.container.element.IntegerElement;
import jp.satomaru.util.container.element.LongElement;
import jp.satomaru.util.container.element.StringElement;

final class ToDouble extends ElementParser<Double> {

	@Override
	public Element<Double> parse(BooleanElement element) throws ElementException {
		return map(element, value -> value ? -1.0D : 0.0D);
	}

	@Override
	public Element<Double> parse(DoubleElement element) throws ElementException {
		return element;
	}

	@Override
	public Element<Double> parse(IntegerElement element) throws ElementException {
		return map(element, Number::doubleValue);
	}

	@Override
	public Element<Double> parse(LongElement element) throws ElementException {
		return map(element, Number::doubleValue);
	}

	@Override
	public Element<Double> parse(StringElement element) throws ElementException {
		return map(element, Double::valueOf);
	}

	@Override
	protected Element<Double> set(Element<?> element, Double newValue) {
		return new DoubleElement(element.id(), newValue);
	}

	@Override
	protected Type typeWhenParseFailure() {
		return Type.PARSE_DOUBLE_FAILURE;
	}
}
