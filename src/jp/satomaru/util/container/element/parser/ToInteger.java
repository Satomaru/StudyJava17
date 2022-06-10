package jp.satomaru.util.container.element.parser;

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

final class ToInteger implements ElementParser<Integer, IntegerElement> {

	@Override
	public IntegerElement set(Element<?> element, Integer newValue) {
		return Element.of(element.id(), newValue);
	}

	@Override
	public <F> IntegerElement parse(Element<F> element, RetArg1<F, Integer> parser) throws ElementException {
		try {
			return set(element, element.isEmpty() ? null : parser.execute(element.value()));
		} catch (ElementException e) {
			throw e;
		} catch (Exception e) {
			throw new ElementException(element, Type.PARSE_INTEGER_FAILURE, e);
		}
	}

	@Override
	public IntegerElement parse(BooleanElement element) throws ElementException {
		return parse(element, value -> value ? -1 : 0);
	}

	@Override
	public IntegerElement parse(InstantElement element) throws ElementException {
		throw new ElementException(element, Type.PARSE_INTEGER_FAILURE);
	}

	@Override
	public IntegerElement parse(DoubleElement element) throws ElementException {
		return parse(element, Number::intValue);
	}

	@Override
	public IntegerElement parse(IntegerElement element) throws ElementException {
		return element;
	}

	@Override
	public IntegerElement parse(LocalDateTimeElement element) throws ElementException {
		throw new ElementException(element, Type.PARSE_INTEGER_FAILURE);
	}

	@Override
	public IntegerElement parse(LongElement element) throws ElementException {
		return parse(element, Number::intValue);
	}

	@Override
	public IntegerElement parse(StringElement element) throws ElementException {
		return parse(element, Integer::valueOf);
	}
}
