package jp.satomaru.util.container.element.parser;

import jp.satomaru.util.container.element.Element;
import jp.satomaru.util.container.element.BooleanElement;
import jp.satomaru.util.container.element.DoubleElement;
import jp.satomaru.util.container.element.ElementException;
import jp.satomaru.util.container.element.InstantElement;
import jp.satomaru.util.container.element.IntegerElement;
import jp.satomaru.util.container.element.LocalDateTimeElement;
import jp.satomaru.util.container.element.LongElement;
import jp.satomaru.util.container.element.StringElement;
import jp.satomaru.util.function.RetArg1;

public sealed interface ElementParser<V, E extends Element<V>> permits ToBoolean, ToDouble, ToInstant, ToInteger, ToLocalDateTime, ToLong, ToString {

	public static final ToBoolean BOOLEAN = new ToBoolean();
	public static final ToDouble DOUBLE = new ToDouble();
	public static final ToInstant INSTANT = new ToInstant();
	public static final ToInteger INTEGER = new ToInteger();
	public static final ToLocalDateTime LOCALDATETIME = new ToLocalDateTime();
	public static final ToLong LONG = new ToLong();
	public static final ToString STRING = new ToString();

	E set(Element<?> element, V newValue);

	<F> E parse(Element<F> element, RetArg1<F, V> parser) throws ElementException;

	E parse(BooleanElement element) throws ElementException;

	E parse(DoubleElement element) throws ElementException;

	E parse(InstantElement element) throws ElementException;

	E parse(IntegerElement element) throws ElementException;

	E parse(LocalDateTimeElement element) throws ElementException;

	E parse(LongElement element) throws ElementException;

	E parse(StringElement element) throws ElementException;
}
