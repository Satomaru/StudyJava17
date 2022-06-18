package jp.satomaru.util.component.element.parser;

import java.util.Set;

import jp.satomaru.util.component.element.BooleanElement;
import jp.satomaru.util.component.element.DoubleElement;
import jp.satomaru.util.component.element.Element;
import jp.satomaru.util.component.element.ElementException;
import jp.satomaru.util.component.element.IntegerElement;
import jp.satomaru.util.component.element.LongElement;
import jp.satomaru.util.component.element.StringElement;
import jp.satomaru.util.component.element.ElementException.ErrorCode;

final class ToBoolean extends ElementParser<Boolean> {

	private static final Set<String> TRUTHY = Set.of("true", "on", "yes", "ok");
	private static final Set<String> FALSY = Set.of("false", "off", "no", "ng");

	@Override
	public Element<Boolean> parse(BooleanElement element) throws ElementException {
		return element;
	}

	@Override
	public Element<Boolean> parse(DoubleElement element) throws ElementException {
		return map(element, value -> {
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
	public Element<Boolean> parse(IntegerElement element) throws ElementException {
		return map(element, value -> {
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
	public Element<Boolean> parse(LongElement element) throws ElementException {
		return map(element, value -> {
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
	public Element<Boolean> parse(StringElement element) throws ElementException {
		return map(element, value -> {
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

	@Override
	protected Element<Boolean> set(Element<?> element, Boolean newValue) {
		return new BooleanElement(element.id(), newValue);
	}

	@Override
	protected ErrorCode typeWhenParseFailure() {
		return ErrorCode.PARSE_BOOLEAN_FAILURE;
	}
}
