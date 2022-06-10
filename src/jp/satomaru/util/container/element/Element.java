package jp.satomaru.util.container.element;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import jp.satomaru.util.container.IdentifiableValue;
import jp.satomaru.util.container.element.ElementException.Type;
import jp.satomaru.util.container.element.parser.ElementParser;

public sealed interface Element<V> extends
	IdentifiableValue<String, V>permits BooleanElement, DoubleElement, InstantElement, IntegerElement, LocalDateTimeElement, LongElement, StringElement {

	public static BooleanElement of(String id, Boolean value) {
		return new BooleanElement(id, value);
	}

	public static DoubleElement of(String id, Double value) {
		return new DoubleElement(id, value);
	}

	public static InstantElement of(String id, Instant value) {
		return new InstantElement(id, value);
	}

	public static IntegerElement of(String id, Integer value) {
		return new IntegerElement(id, value);
	}

	public static LocalDateTimeElement of(String id, LocalDateTime value) {
		return new LocalDateTimeElement(id, value);
	}

	public static LongElement of(String id, Long value) {
		return new LongElement(id, value);
	}

	public static StringElement of(String id, String value) {
		return new StringElement(id, value);
	}

	@Override
	String id();

	@Override
	V value();

	<E extends Element<?>> E accept(ElementParser<?, E> parser) throws ElementException;

	default Optional<V> optional() {
		return Optional.ofNullable(value());
	}

	default boolean isEmpty() {
		return optional().isEmpty();
	}

	default V orElseThrow() throws ElementException {
		return optional().orElseThrow(() -> new ElementException(this, Type.EMPTY));
	}
}
