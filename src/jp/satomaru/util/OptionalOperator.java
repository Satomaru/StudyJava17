package jp.satomaru.util;

import java.util.Optional;
import java.util.function.Predicate;

public interface OptionalOperator<T> {

	Optional<T> optional();

	default boolean isPresent() {
		return optional().isPresent();
	}

	default boolean isEmpty() {
		return optional().isEmpty();
	}

	default boolean is(Predicate<? super T> predicate) {
		return optional().filter(predicate).isPresent();
	}

	default boolean not(Predicate<? super T> predicate) {
		return is(predicate.negate());
	}

	default boolean eq(Object other) {
		return is(value -> value.equals(other));
	}

	default boolean ne(Object other) {
		return not(value -> value.equals(other));
	}

	default <C extends T> Optional<C> cast(Class<C> type) {
		return optional().map(type::cast);
	}
}
