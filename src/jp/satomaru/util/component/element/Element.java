package jp.satomaru.util.component.element;

import java.util.Optional;

import jp.satomaru.util.component.ComponentException;
import jp.satomaru.util.component.ComponentException.ErrorCode;
import jp.satomaru.util.component.ComponentId;
import jp.satomaru.util.component.element.mapper.ElementMapper;

public sealed interface Element<V> permits BooleanElement, DoubleElement, EmptyElement<V>, InstantElement, IntegerElement, LocalDateTimeElement, LongElement, StringElement {

	ComponentId id();

	V value();

	<T> Element<T> map(ElementMapper<T> mapper) throws ComponentException;

	Element<V> rename(String newName);

	default Optional<V> optional() {
		return Optional.ofNullable(value());
	}

	default boolean isEmpty() {
		return optional().isEmpty();
	}

	default <P> P parse(ElementMapper<P> mapper) throws ComponentException {
		return map(mapper).value();
	}

	default <P> P parseOrThrow(ElementMapper<P> mapper) throws ComponentException {
		if (isEmpty()) {
			throw new ComponentException(id(), ErrorCode.EMPTY, value());
		}

		return parse(mapper);
	}
}
