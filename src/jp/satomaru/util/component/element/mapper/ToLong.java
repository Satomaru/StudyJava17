package jp.satomaru.util.component.element.mapper;

import java.time.ZoneId;

import jp.satomaru.util.component.ComponentException;
import jp.satomaru.util.component.ComponentException.ErrorCode;
import jp.satomaru.util.component.element.BooleanElement;
import jp.satomaru.util.component.element.DoubleElement;
import jp.satomaru.util.component.element.Element;
import jp.satomaru.util.component.element.InstantElement;
import jp.satomaru.util.component.element.IntegerElement;
import jp.satomaru.util.component.element.LocalDateTimeElement;
import jp.satomaru.util.component.element.LongElement;
import jp.satomaru.util.component.element.StringElement;

final class ToLong extends ElementMapper<Long> {

	@Override
	public Element<Long> map(BooleanElement element) throws ComponentException {
		return map(element, value -> value ? -1L : 0L);
	}

	@Override
	public Element<Long> map(DoubleElement element) throws ComponentException {
		return map(element, Number::longValue);
	}

	@Override
	public Element<Long> map(InstantElement element) throws ComponentException {
		return map(element, value -> value.toEpochMilli());
	}

	@Override
	public Element<Long> map(IntegerElement element) throws ComponentException {
		return map(element, Number::longValue);
	}

	@Override
	public Element<Long> map(LocalDateTimeElement element) throws ComponentException {
		return map(element, value -> value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
	}

	@Override
	public Element<Long> map(LongElement element) throws ComponentException {
		return element;
	}

	@Override
	public Element<Long> map(StringElement element) throws ComponentException {
		return map(element, Long::valueOf);
	}

	@Override
	protected Element<Long> create(Element<?> element, Long newValue) {
		return new LongElement(element.id(), newValue);
	}

	@Override
	protected ErrorCode errorCode() {
		return ErrorCode.PARSE_LONG_FAILURE;
	}
}
