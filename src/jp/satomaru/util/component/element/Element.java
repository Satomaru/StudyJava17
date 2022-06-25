package jp.satomaru.util.component.element;

import java.util.Optional;
import java.util.function.Predicate;

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

	default V orElseThrow() throws ComponentException {
		return optional().orElseThrow(() -> new ComponentException(id(), ErrorCode.EMPTY, value()));
	}

	default Element<V> must(Predicate<V> predicate) throws ComponentException {
		if (!isEmpty()) {
			if (!predicate.test(value())) {
				throw new ComponentException(id(), ErrorCode.ILLEGAL_VALUE, value());
			}
		}

		return this;
	}
}
