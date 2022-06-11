package jp.satomaru.util.container.element.parser;

import java.time.ZoneId;

import jp.satomaru.util.container.element.BooleanElement;
import jp.satomaru.util.container.element.DoubleElement;
import jp.satomaru.util.container.element.Element;
import jp.satomaru.util.container.element.ElementException;
import jp.satomaru.util.container.element.ElementException.Type;
import jp.satomaru.util.container.element.InstantElement;
import jp.satomaru.util.container.element.IntegerElement;
import jp.satomaru.util.container.element.LocalDateTimeElement;
import jp.satomaru.util.container.element.LongElement;
import jp.satomaru.util.container.element.StringElement;

final class ToLong extends ElementParser<Long, LongElement> {

	@Override
	public LongElement parse(BooleanElement element) throws ElementException {
		return parse(element, value -> value ? -1L : 0L);
	}

	@Override
	public LongElement parse(DoubleElement element) throws ElementException {
		return parse(element, Number::longValue);
	}

	@Override
	public LongElement parse(InstantElement element) throws ElementException {
		return parse(element, value -> value.toEpochMilli());
	}

	@Override
	public LongElement parse(IntegerElement element) throws ElementException {
		return parse(element, Number::longValue);
	}

	@Override
	public LongElement parse(LocalDateTimeElement element) throws ElementException {
		return parse(element, value -> value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
	}

	@Override
	public LongElement parse(LongElement element) throws ElementException {
		return element;
	}

	@Override
	public LongElement parse(StringElement element) throws ElementException {
		return parse(element, Long::valueOf);
	}

	@Override
	protected LongElement set(Element<?> element, Long newValue) {
		return new LongElement(element.id(), newValue);
	}

	@Override
	protected Type typeWhenParseFailure() {
		return Type.PARSE_LONG_FAILURE;
	}
}
