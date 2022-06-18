package jp.satomaru.util.component.element.parser;

import java.time.Instant;
import java.time.ZoneId;

import jp.satomaru.util.component.element.Element;
import jp.satomaru.util.component.element.ElementException;
import jp.satomaru.util.component.element.InstantElement;
import jp.satomaru.util.component.element.LocalDateTimeElement;
import jp.satomaru.util.component.element.LongElement;
import jp.satomaru.util.component.element.StringElement;
import jp.satomaru.util.component.element.ElementException.ErrorCode;

final class ToInstant extends ElementParser<Instant> {

	@Override
	public Element<Instant> parse(InstantElement element) throws ElementException {
		return element;
	}

	@Override
	public Element<Instant> parse(LocalDateTimeElement element) throws ElementException {
		return map(element, value -> value.atZone(ZoneId.systemDefault()).toInstant());
	}

	@Override
	public Element<Instant> parse(LongElement element) throws ElementException {
		return map(element, Instant::ofEpochMilli);
	}

	@Override
	public Element<Instant> parse(StringElement element) throws ElementException {
		return map(element, Instant::parse);
	}

	@Override
	protected Element<Instant> set(Element<?> element, Instant newValue) {
		return new InstantElement(element.id(), newValue);
	}

	@Override
	protected ErrorCode typeWhenParseFailure() {
		return ErrorCode.PARSE_INSTANT_FAILURE;
	}
}
