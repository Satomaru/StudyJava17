package jp.satomaru.util.component.element.parser;

import java.time.ZoneId;

import jp.satomaru.util.component.element.BooleanElement;
import jp.satomaru.util.component.element.DoubleElement;
import jp.satomaru.util.component.element.Element;
import jp.satomaru.util.component.element.ElementException;
import jp.satomaru.util.component.element.InstantElement;
import jp.satomaru.util.component.element.IntegerElement;
import jp.satomaru.util.component.element.LocalDateTimeElement;
import jp.satomaru.util.component.element.LongElement;
import jp.satomaru.util.component.element.StringElement;
import jp.satomaru.util.component.element.ElementException.ErrorCode;

final class ToLong extends ElementParser<Long> {

	@Override
	public Element<Long> parse(BooleanElement element) throws ElementException {
		return map(element, value -> value ? -1L : 0L);
	}

	@Override
	public Element<Long> parse(DoubleElement element) throws ElementException {
		return map(element, Number::longValue);
	}

	@Override
	public Element<Long> parse(InstantElement element) throws ElementException {
		return map(element, value -> value.toEpochMilli());
	}

	@Override
	public Element<Long> parse(IntegerElement element) throws ElementException {
		return map(element, Number::longValue);
	}

	@Override
	public Element<Long> parse(LocalDateTimeElement element) throws ElementException {
		return map(element, value -> value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
	}

	@Override
	public Element<Long> parse(LongElement element) throws ElementException {
		return element;
	}

	@Override
	public Element<Long> parse(StringElement element) throws ElementException {
		return map(element, Long::valueOf);
	}

	@Override
	protected Element<Long> set(Element<?> element, Long newValue) {
		return new LongElement(element.id(), newValue);
	}

	@Override
	protected ErrorCode typeWhenParseFailure() {
		return ErrorCode.PARSE_LONG_FAILURE;
	}
}
