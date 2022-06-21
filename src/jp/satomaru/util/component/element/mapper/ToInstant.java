package jp.satomaru.util.component.element.mapper;

import java.time.Instant;
import java.time.ZoneId;

import jp.satomaru.util.component.ComponentException;
import jp.satomaru.util.component.ComponentException.ErrorCode;
import jp.satomaru.util.component.element.Element;
import jp.satomaru.util.component.element.InstantElement;
import jp.satomaru.util.component.element.LocalDateTimeElement;
import jp.satomaru.util.component.element.LongElement;
import jp.satomaru.util.component.element.StringElement;

final class ToInstant extends ElementMapper<Instant> {

	@Override
	public Element<Instant> map(InstantElement element) throws ComponentException {
		return element;
	}

	@Override
	public Element<Instant> map(LocalDateTimeElement element) throws ComponentException {
		return map(element, value -> value.atZone(ZoneId.systemDefault()).toInstant());
	}

	@Override
	public Element<Instant> map(LongElement element) throws ComponentException {
		return map(element, Instant::ofEpochMilli);
	}

	@Override
	public Element<Instant> map(StringElement element) throws ComponentException {
		return map(element, Instant::parse);
	}

	@Override
	protected Element<Instant> create(Element<?> element, Instant newValue) {
		return new InstantElement(element.id(), newValue);
	}

	@Override
	protected ErrorCode errorCode() {
		return ErrorCode.PARSE_INSTANT_FAILURE;
	}
}
