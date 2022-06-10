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

final class ToDouble implements ElementParser<Double, DoubleElement> {

	@Override
	public DoubleElement set(Element<?> element, Double newValue) {
		return Element.of(element.id(), newValue);
	}

	@Override
	public <F> DoubleElement parse(Element<F> element, RetArg1<F, Double> parser) throws ElementException {
		try {
			return set(element, element.isEmpty() ? null : parser.execute(element.value()));
		} catch (ElementException e) {
			throw e;
		} catch (Exception e) {
			throw new ElementException(element, Type.PARSE_DOUBLE_FAILURE, e);
		}
	}

	@Override
	public DoubleElement parse(BooleanElement element) throws ElementException {
		return parse(element, value -> value ? -1.0D : 0.0D);
	}

	@Override
	public DoubleElement parse(DoubleElement element) throws ElementException {
		return element;
	}

	@Override
	public DoubleElement parse(InstantElement element) throws ElementException {
		throw new ElementException(element, Type.PARSE_DOUBLE_FAILURE);
	}

	@Override
	public DoubleElement parse(IntegerElement element) throws ElementException {
		return parse(element, Number::doubleValue);
	}

	@Override
	public DoubleElement parse(LocalDateTimeElement element) throws ElementException {
		throw new ElementException(element, Type.PARSE_DOUBLE_FAILURE);
	}

	@Override
	public DoubleElement parse(LongElement element) throws ElementException {
		return parse(element, Number::doubleValue);
	}

	@Override
	public DoubleElement parse(StringElement element) throws ElementException {
		return parse(element, Double::valueOf);
	}
}
