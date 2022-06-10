package jp.satomaru.util.container.element.parser;

import java.util.Set;

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
import jp.satomaru.util.function.RetArg1;

final class ToBoolean implements ElementParser<Boolean, BooleanElement> {

	private static final Set<String> TRUTHY = Set.of("true", "on", "yes", "ok");
	private static final Set<String> FALSY = Set.of("false", "off", "no", "ng");

	@Override
	public BooleanElement set(Element<?> element, Boolean newValue) {
		return Element.of(element.id(), newValue);
	}

	@Override
	public <F> BooleanElement parse(Element<F> element, RetArg1<F, Boolean> parser) throws ElementException {
		try {
			return set(element, element.isEmpty() ? null : parser.execute(element.value()));
		} catch (ElementException e) {
			throw e;
		} catch (Exception e) {
			throw new ElementException(element, Type.PARSE_BOOLEAN_FAILURE, e);
		}
	}

	@Override
	public BooleanElement parse(BooleanElement element) throws ElementException {
		return element;
	}

	@Override
	public BooleanElement parse(DoubleElement element) throws ElementException {
		return parse(element, value -> {
			if (value == -1.0D) {
				return true;
			}

			if (value == 0.0D) {
				return false;
			}

			throw new IllegalArgumentException("value must be -1.0D or 0.0D");
		});
	}

	@Override
	public BooleanElement parse(InstantElement element) throws ElementException {
		throw new ElementException(element, Type.PARSE_BOOLEAN_FAILURE);
	}

	@Override
	public BooleanElement parse(IntegerElement element) throws ElementException {
		return parse(element, value -> {
			if (value == -1) {
				return true;
			}

			if (value == 0) {
				return false;
			}

			throw new IllegalArgumentException("value must be -1 or 0");
		});
	}

	@Override
	public BooleanElement parse(LocalDateTimeElement element) throws ElementException {
		throw new ElementException(element, Type.PARSE_BOOLEAN_FAILURE);
	}

	@Override
	public BooleanElement parse(LongElement element) throws ElementException {
		return parse(element, value -> {
			if (value == -1L) {
				return true;
			}

			if (value == 0L) {
				return false;
			}

			throw new IllegalArgumentException("value must be -1L or 0L");
		});
	}

	@Override
	public BooleanElement parse(StringElement element) throws ElementException {
		return parse(element, value -> {
			String lowerCase = value.toLowerCase();

			if (TRUTHY.contains(lowerCase)) {
				return true;
			}

			if (FALSY.contains(lowerCase)) {
				return false;
			}

			throw new IllegalArgumentException(
				String.format("value must be %s or %s", TRUTHY, FALSY));
		});
	}
}
