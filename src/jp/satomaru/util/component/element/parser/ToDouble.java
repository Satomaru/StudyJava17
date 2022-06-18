package jp.satomaru.util.component.element.parser;

import jp.satomaru.util.component.element.BooleanElement;
import jp.satomaru.util.component.element.DoubleElement;
import jp.satomaru.util.component.element.Element;
import jp.satomaru.util.component.element.ElementException;
import jp.satomaru.util.component.element.IntegerElement;
import jp.satomaru.util.component.element.LongElement;
import jp.satomaru.util.component.element.StringElement;
import jp.satomaru.util.component.element.ElementException.ErrorCode;

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
	protected ErrorCode typeWhenParseFailure() {
		return ErrorCode.PARSE_DOUBLE_FAILURE;
	}
}
