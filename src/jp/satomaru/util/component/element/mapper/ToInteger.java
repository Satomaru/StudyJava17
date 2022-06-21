package jp.satomaru.util.component.element.mapper;

import jp.satomaru.util.component.ComponentException;
import jp.satomaru.util.component.ComponentException.ErrorCode;
import jp.satomaru.util.component.element.BooleanElement;
import jp.satomaru.util.component.element.DoubleElement;
import jp.satomaru.util.component.element.Element;
import jp.satomaru.util.component.element.IntegerElement;
import jp.satomaru.util.component.element.LongElement;
import jp.satomaru.util.component.element.StringElement;

final class ToInteger extends ElementMapper<Integer> {

	@Override
	public Element<Integer> map(BooleanElement element) throws ComponentException {
		return map(element, value -> value ? -1 : 0);
	}

	@Override
	public Element<Integer> map(DoubleElement element) throws ComponentException {
		return map(element, Number::intValue);
	}

	@Override
	public Element<Integer> map(IntegerElement element) throws ComponentException {
		return element;
	}

	@Override
	public Element<Integer> map(LongElement element) throws ComponentException {
		return map(element, Number::intValue);
	}

	@Override
	public Element<Integer> map(StringElement element) throws ComponentException {
		return map(element, Integer::valueOf);
	}

	@Override
	protected Element<Integer> create(Element<?> element, Integer newValue) {
		return new IntegerElement(element.id(), newValue);
	}

	@Override
	protected ErrorCode errorCode() {
		return ErrorCode.PARSE_INTEGER_FAILURE;
	}
}
