package jp.satomaru.util.component.element.parser;

import java.time.format.DateTimeFormatter;

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

final class ToString extends ElementParser<String> {

	private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");

	@Override
	public Element<String> parse(BooleanElement element) throws ElementException {
		return map(element, Object::toString);
	}

	@Override
	public Element<String> parse(DoubleElement element) throws ElementException {
		return map(element, Object::toString);
	}

	@Override
	public Element<String> parse(InstantElement element) throws ElementException {
		return map(element, Object::toString);
	}

	@Override
	public Element<String> parse(IntegerElement element) throws ElementException {
		return map(element, Object::toString);
	}

	@Override
	public Element<String> parse(LocalDateTimeElement element) throws ElementException {
		return map(element, value -> value.format(DATETIME_FORMATTER));
	}

	@Override
	public Element<String> parse(LongElement element) throws ElementException {
		return map(element, Object::toString);
	}

	@Override
	public Element<String> parse(StringElement element) throws ElementException {
		return element;
	}

	@Override
	protected Element<String> set(Element<?> element, String newValue) {
		return new StringElement(element.id(), newValue);
	}

	@Override
	protected ErrorCode typeWhenParseFailure() {
		return ErrorCode.PARSE_STRING_FAILURE;
	}
}
