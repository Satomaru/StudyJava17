package jp.satomaru.util;

import java.util.function.Predicate;
import java.util.stream.Stream;

public final class Tester<T> {

	public static Predicate<Object> sameOrEquals(Object other) {
		return value -> (value == other) || (value != null && value.equals(other));
	}

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

	public static <T extends Comparable<T>> Predicate<T> range(T min, T maxExclusive) {
		return value -> value.compareTo(min) >= 0 && value.compareTo(maxExclusive) < 0;
	}

	public static <T extends Comparable<T>> Predicate<T> outOfRange(T min, T maxExclusive) {
		return value -> value.compareTo(min) < 0 || value.compareTo(maxExclusive) >= 0;
	}

	@SafeVarargs
	public static <T extends Comparable<T>> Predicate<T> oneOf(T... others) {
		return value -> Stream.of(others).anyMatch(other -> value.compareTo(other) == 0);
	}

	@SafeVarargs
	public static <T extends Comparable<T>> Predicate<T> notOneOf(T... others) {
		return value -> Stream.of(others).allMatch(other -> value.compareTo(other) != 0);
	}

	public static <T> Tester<T> of(String name, T target) {
		return new Tester<>(name, target);
	}

	public static <T> T must(String name, T target, Predicate<T> predicate) {
		return new Tester<>(name, target).must(predicate).get();
	}

	private final String name;

	private final T target;

	private Tester(String name, T target) {
		this.name = name;
		this.target = target;
	}

	public Tester<T> must(Predicate<T> predicate) {
		if (target != null && !predicate.test(target)) {
			throw new IllegalArgumentException(name);
		}

		return this;
	}

	public T get() {
		if (target == null) {
			throw new NullPointerException(name);
		}

		return target;
	}

	public T orElse(T whenNull) {
		return (target != null) ? target : whenNull;
	}
}
