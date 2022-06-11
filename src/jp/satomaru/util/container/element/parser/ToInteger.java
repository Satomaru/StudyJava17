package jp.satomaru.util.container.element.parser;

import jp.satomaru.util.container.element.BooleanElement;
import jp.satomaru.util.container.element.DoubleElement;
import jp.satomaru.util.container.element.Element;
import jp.satomaru.util.container.element.ElementException;
import jp.satomaru.util.container.element.ElementException.Type;
import jp.satomaru.util.container.element.IntegerElement;
import jp.satomaru.util.container.element.LongElement;
import jp.satomaru.util.container.element.StringElement;

final class ToInteger extends ElementParser<Integer, IntegerElement> {

	@Override
	public IntegerElement parse(BooleanElement element) throws ElementException {
		return parse(element, value -> value ? -1 : 0);
	}

	@Override
	public IntegerElement parse(DoubleElement element) throws ElementException {
		return parse(element, Number::intValue);
	}

	@Override
	public IntegerElement parse(IntegerElement element) throws ElementException {
		return element;
	}

	@Override
	public IntegerElement parse(LongElement element) throws ElementException {
		return parse(element, Number::intValue);
	}

	@Override
	public IntegerElement parse(StringElement element) throws ElementException {
		return parse(element, Integer::valueOf);
	}

	@Override
	protected IntegerElement set(Element<?> element, Integer newValue) {
		return new IntegerElement(element.id(), newValue);
	}

	@Override
	protected Type typeWhenParseFailure() {
		return Type.PARSE_INTEGER_FAILURE;
	}
}
