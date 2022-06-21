package jp.satomaru.util.component.element.mapper;

import java.util.Set;

import jp.satomaru.util.component.ComponentException;
import jp.satomaru.util.component.ComponentException.ErrorCode;
import jp.satomaru.util.component.element.BooleanElement;
import jp.satomaru.util.component.element.DoubleElement;
import jp.satomaru.util.component.element.Element;
import jp.satomaru.util.component.element.IntegerElement;
import jp.satomaru.util.component.element.LongElement;
import jp.satomaru.util.component.element.StringElement;

final class ToBoolean extends ElementMapper<Boolean> {

	private static final Set<String> TRUTHY = Set.of("true", "on", "yes", "ok");
	private static final Set<String> FALSY = Set.of("false", "off", "no", "ng");

	@Override
	public Element<Boolean> map(BooleanElement element) throws ComponentException {
		return element;
	}

	@Override
	public Element<Boolean> map(DoubleElement element) throws ComponentException {
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
	public Element<Boolean> map(IntegerElement element) throws ComponentException {
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
	public Element<Boolean> map(LongElement element) throws ComponentException {
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
	public Element<Boolean> map(StringElement element) throws ComponentException {
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
	protected Element<Boolean> create(Element<?> element, Boolean newValue) {
		return new BooleanElement(element.id(), newValue);
	}

	@Override
	protected ErrorCode errorCode() {
		return ErrorCode.PARSE_BOOLEAN_FAILURE;
	}
}
