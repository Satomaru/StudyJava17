package jp.satomaru.util.component.element.mapper;

import java.time.format.DateTimeFormatter;

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

final class ToString extends ElementMapper<String> {

	private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");

	@Override
	public Element<String> map(BooleanElement element) throws ComponentException {
		return map(element, Object::toString);
	}

	@Override
	public Element<String> map(DoubleElement element) throws ComponentException {
		return map(element, Object::toString);
	}

	@Override
	public Element<String> map(InstantElement element) throws ComponentException {
		return map(element, Object::toString);
	}

	@Override
	public Element<String> map(IntegerElement element) throws ComponentException {
		return map(element, Object::toString);
	}

	@Override
	public Element<String> map(LocalDateTimeElement element) throws ComponentException {
		return map(element, value -> value.format(DATETIME_FORMATTER));
	}

	@Override
	public Element<String> map(LongElement element) throws ComponentException {
		return map(element, Object::toString);
	}

	@Override
	public Element<String> map(StringElement element) throws ComponentException {
		return element;
	}

	@Override
	protected Element<String> create(Element<?> element, String newValue) {
		return new StringElement(element.id(), newValue);
	}

	@Override
	protected ErrorCode errorCode() {
		return ErrorCode.PARSE_STRING_FAILURE;
	}
}
