package jp.satomaru.util;

import java.util.function.Predicate;
import java.util.stream.Stream;

public final class Tester {

	public static <T extends Comparable<T>> Predicate<T> not(T other) {
		return value -> value.compareTo(other) != 0;
	}

	public static <T extends Comparable<T>> Predicate<T> lt(T other) {
		return value -> value.compareTo(other) < 0;
	}

	public static <T extends Comparable<T>> Predicate<T> le(T other) {
		return value -> value.compareTo(other) <= 0;
	}

	public static <T extends Comparable<T>> Predicate<T> gt(T other) {
		return value -> value.compareTo(other) > 0;
	}

	public static <T extends Comparable<T>> Predicate<T> ge(T other) {
		return value -> value.compareTo(other) >= 0;
	}

	public static <T extends Comparable<T>> Predicate<T> range(T min, T max) {
		return value -> value.compareTo(min) >= 0 && value.compareTo(max) <= 0;
	}

	public static <T extends Comparable<T>> Predicate<T> outOfRange(T min, T max) {
		return value -> value.compareTo(min) < 0 || value.compareTo(max) > 0;
	}

	@SafeVarargs
	public static <T extends Comparable<T>> Predicate<T> oneOf(T... others) {
		return value -> Stream.of(others).anyMatch(other -> value.compareTo(other) == 0);
	}

	@SafeVarargs
	public static <T extends Comparable<T>> Predicate<T> notOneOf(T... others) {
		return value -> Stream.of(others).allMatch(other -> value.compareTo(other) != 0);
	}

	private Tester() {
	}
}
