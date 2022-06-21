package jp.satomaru.util.component.element.mapper;

import jp.satomaru.util.component.ComponentException;
import jp.satomaru.util.component.ComponentException.ErrorCode;
import jp.satomaru.util.component.element.BooleanElement;
import jp.satomaru.util.component.element.DoubleElement;
import jp.satomaru.util.component.element.Element;
import jp.satomaru.util.component.element.IntegerElement;
import jp.satomaru.util.component.element.LongElement;
import jp.satomaru.util.component.element.StringElement;

final class ToDouble extends ElementMapper<Double> {

	@Override
	public Element<Double> map(BooleanElement element) throws ComponentException {
		return map(element, value -> value ? -1.0D : 0.0D);
	}

	@Override
	public Element<Double> map(DoubleElement element) throws ComponentException {
		return element;
	}

	@Override
	public Element<Double> map(IntegerElement element) throws ComponentException {
		return map(element, Number::doubleValue);
	}

	@Override
	public Element<Double> map(LongElement element) throws ComponentException {
		return map(element, Number::doubleValue);
	}

	@Override
	public Element<Double> map(StringElement element) throws ComponentException {
		return map(element, Double::valueOf);
	}

	@Override
	protected Element<Double> create(Element<?> element, Double newValue) {
		return new DoubleElement(element.id(), newValue);
	}

	@Override
	protected ErrorCode errorCode() {
		return ErrorCode.PARSE_DOUBLE_FAILURE;
	}
}
