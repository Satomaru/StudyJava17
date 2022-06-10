package jp.satomaru.util.container.element.parser;

import jp.satomaru.util.container.element.BooleanElement;
import jp.satomaru.util.container.element.DoubleElement;
import jp.satomaru.util.container.element.Element;
import jp.satomaru.util.container.element.ElementException;
import jp.satomaru.util.container.element.ElementException.Type;
import jp.satomaru.util.container.element.IntegerElement;
import jp.satomaru.util.container.element.LongElement;
import jp.satomaru.util.container.element.StringElement;

final class ToDouble extends ElementParser<Double, DoubleElement> {

	@Override
	public DoubleElement parse(BooleanElement element) throws ElementException {
		return parse(element, value -> value ? -1.0D : 0.0D);
	}

	@Override
	public DoubleElement parse(DoubleElement element) throws ElementException {
		return element;
	}

	@Override
	public DoubleElement parse(IntegerElement element) throws ElementException {
		return parse(element, Number::doubleValue);
	}

	@Override
	public DoubleElement parse(LongElement element) throws ElementException {
		return parse(element, Number::doubleValue);
	}

	@Override
	public DoubleElement parse(StringElement element) throws ElementException {
		return parse(element, Double::valueOf);
	}

	@Override
	protected DoubleElement set(Element<?> element, Double newValue) {
		return Element.of(element.id(), newValue);
	}

	@Override
	protected Type typeWhenParseFailure() {
		return Type.PARSE_DOUBLE_FAILURE;
	}
}
