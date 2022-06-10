package jp.satomaru.util.container.element.parser;

import java.time.format.DateTimeFormatter;

import jp.satomaru.util.container.element.Element;
import jp.satomaru.util.container.element.BooleanElement;
import jp.satomaru.util.container.element.DoubleElement;
import jp.satomaru.util.container.element.ElementException;
import jp.satomaru.util.container.element.ElementException.Type;
import jp.satomaru.util.container.element.InstantElement;
import jp.satomaru.util.container.element.IntegerElement;
import jp.satomaru.util.container.element.LocalDateTimeElement;
import jp.satomaru.util.container.element.LongElement;
import jp.satomaru.util.container.element.StringElement;
import jp.satomaru.util.function.RetArg1;

final class ToString implements ElementParser<String, StringElement> {

	private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");

	@Override
	public StringElement set(Element<?> element, String newValue) {
		return Element.of(element.id(), newValue);
	}

	@Override
	public <F> StringElement parse(Element<F> element, RetArg1<F, String> parser) throws ElementException {
		try {
			return set(element, element.isEmpty() ? null : parser.execute(element.value()));
		} catch (ElementException e) {
			throw e;
		} catch (Exception e) {
			throw new ElementException(element, Type.PARSE_STRING_FAILURE, e);
		}
	}

	@Override
	public StringElement parse(BooleanElement element) throws ElementException {
		return parse(element, Object::toString);
	}

	@Override
	public StringElement parse(DoubleElement element) throws ElementException {
		return parse(element, Object::toString);
	}

	@Override
	public StringElement parse(InstantElement element) throws ElementException {
		return parse(element, Object::toString);
	}

	@Override
	public StringElement parse(IntegerElement element) throws ElementException {
		return parse(element, Object::toString);
	}

	@Override
	public StringElement parse(LocalDateTimeElement element) throws ElementException {
		return parse(element, value -> value.format(DATETIME_FORMATTER));
	}

	@Override
	public StringElement parse(LongElement element) throws ElementException {
		return parse(element, Object::toString);
	}

	@Override
	public StringElement parse(StringElement element) throws ElementException {
		return element;
	}
}