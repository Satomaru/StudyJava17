package jp.satomaru.util.component.element.parser;

import jp.satomaru.util.component.element.BooleanElement;
import jp.satomaru.util.component.element.DoubleElement;
import jp.satomaru.util.component.element.Element;
import jp.satomaru.util.component.element.ElementException;
import jp.satomaru.util.component.element.IntegerElement;
import jp.satomaru.util.component.element.LongElement;
import jp.satomaru.util.component.element.StringElement;
import jp.satomaru.util.component.element.ElementException.ErrorCode;

final class ToInteger extends ElementParser<Integer> {

	@Override
	public Element<Integer> parse(BooleanElement element) throws ElementException {
		return map(element, value -> value ? -1 : 0);
	}

	@Override
	public Element<Integer> parse(DoubleElement element) throws ElementException {
		return map(element, Number::intValue);
	}

	@Override
	public Element<Integer> parse(IntegerElement element) throws ElementException {
		return element;
	}

	@Override
	public Element<Integer> parse(LongElement element) throws ElementException {
		return map(element, Number::intValue);
	}

	@Override
	public Element<Integer> parse(StringElement element) throws ElementException {
		return map(element, Integer::valueOf);
	}

	@Override
	protected Element<Integer> set(Element<?> element, Integer newValue) {
		return new IntegerElement(element.id(), newValue);
	}

	@Override
	protected ErrorCode typeWhenParseFailure() {
		return ErrorCode.PARSE_INTEGER_FAILURE;
	}
}
