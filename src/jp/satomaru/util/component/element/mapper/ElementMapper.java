package jp.satomaru.util.component.element.mapper;

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
import jp.satomaru.util.function.RetArg1;

public sealed abstract class ElementMapper<V> permits ToBoolean, ToDouble, ToInstant, ToInteger, ToLocalDateTime, ToLong, ToString {

	public static final ToBoolean BOOLEAN = new ToBoolean();
	public static final ToDouble DOUBLE = new ToDouble();
	public static final ToInstant INSTANT = new ToInstant();
	public static final ToInteger INTEGER = new ToInteger();
	public static final ToLocalDateTime LOCALDATETIME = new ToLocalDateTime();
	public static final ToLong LONG = new ToLong();
	public static final ToString STRING = new ToString();

	public Element<V> map(BooleanElement element) throws ComponentException {
		throw fail(element);
	}

	public Element<V> map(DoubleElement element) throws ComponentException {
		throw fail(element);
	}

	public Element<V> map(InstantElement element) throws ComponentException {
		throw fail(element);
	}

	public Element<V> map(IntegerElement element) throws ComponentException {
		throw fail(element);
	}

	public Element<V> map(LocalDateTimeElement element) throws ComponentException {
		throw fail(element);
	}

	public Element<V> map(LongElement element) throws ComponentException {
		throw fail(element);
	}

	public Element<V> map(StringElement element) throws ComponentException {
		throw fail(element);
	}

	protected final <F> Element<V> map(Element<F> element, RetArg1<F, V> mapper) throws ComponentException {
		try {
			return create(element, mapper.execute(element.value()));
		} catch (ComponentException e) {
			throw e;
		} catch (Exception e) {
			throw fail(element, e);
		}
	}

	protected final ComponentException fail(Element<?> element) {
		return new ComponentException(element.id(), errorCode(), element.value());
	}

	protected final ComponentException fail(Element<?> element, Throwable cause) {
		return new ComponentException(element.id(), errorCode(), element.value(), cause);
	}

	protected abstract Element<V> create(Element<?> element, V newValue);

	protected abstract ErrorCode errorCode();
}
