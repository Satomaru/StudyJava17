package jp.satomaru.util.container.element.parser;

import java.time.Instant;
import java.time.ZoneId;

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

final class ToInstant implements ElementParser<Instant, InstantElement> {

	@Override
	public InstantElement set(Element<?> element, Instant newValue) {
		return Element.of(element.id(), newValue);
	}

	@Override
	public <F> InstantElement parse(Element<F> element, RetArg1<F, Instant> parser) throws ElementException {
		try {
			return set(element, element.isEmpty() ? null : parser.execute(element.value()));
		} catch (ElementException e) {
			throw e;
		} catch (Exception e) {
			throw new ElementException(element, Type.PARSE_INSTANT_FAILURE, e);
		}
	}

	@Override
	public InstantElement parse(BooleanElement element) throws ElementException {
		throw new ElementException(element, Type.PARSE_INSTANT_FAILURE);
	}

	@Override
	public InstantElement parse(DoubleElement element) throws ElementException {
		throw new ElementException(element, Type.PARSE_INSTANT_FAILURE);
	}

	@Override
	public InstantElement parse(InstantElement element) throws ElementException {
		return element;
	}

	@Override
	public InstantElement parse(IntegerElement element) throws ElementException {
		throw new ElementException(element, Type.PARSE_INSTANT_FAILURE);
	}

	@Override
	public InstantElement parse(LocalDateTimeElement element) throws ElementException {
		return parse(element, value -> value.atZone(ZoneId.systemDefault()).toInstant());
	}

	@Override
	public InstantElement parse(LongElement element) throws ElementException {
		return parse(element, Instant::ofEpochMilli);
	}

	@Override
	public InstantElement parse(StringElement element) throws ElementException {
		return parse(element, Instant::parse);
	}
}
