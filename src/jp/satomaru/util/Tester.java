package jp.satomaru.util;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * 検査ユーティリティ。
 *
 * @author Satomaru
 *
 * @param <T> 検査する値
 */
public final class Tester<T> {

	/**
	 * 値が等しいことを検査します。
	 *
	 * @param <T>   検査する値
	 * @param other 期待する値（null可）
	 * @return 検査関数
	 */
	public static <T> Predicate<? super T> eq(T other) {
		return value -> {
			if (value == other) {
				return true;
			} else if (value == null || other == null) {
				return false;
			} else {
				return value.equals(other);
			}
		};
	}

	/**
	 * 値が等しくないことを検査します。
	 *
	 * @param <T>   検査する値
	 * @param other 期待しない値（null可）
	 * @return 検査関数
	 */
	public static <T> Predicate<? super T> ne(T other) {
		return eq(other).negate();
	}

	/**
	 * 比較可能な値において、比較対象未満であることを検査します。
	 *
	 * @param <T>   検査する値
	 * @param other 比較対象（null不可）
	 * @return 検査関数
	 */
	public static <T extends Comparable<T>> Predicate<T> lt(T other) {
		return value -> value.compareTo(other) < 0;
	}

	/**
	 * 比較可能な値において、比較対象以下であることを検査します。
	 *
	 * @param <T>   検査する値
	 * @param other 比較対象（null不可）
	 * @return 検査関数
	 */
	public static <T extends Comparable<T>> Predicate<T> le(T other) {
		return value -> value.compareTo(other) <= 0;
	}

	/**
	 * 比較可能な値において、比較対象超過であることを検査します。
	 *
	 * @param <T>   検査する値
	 * @param other 比較対象（null不可）
	 * @return 検査関数
	 */
	public static <T extends Comparable<T>> Predicate<T> gt(T other) {
		return value -> value.compareTo(other) > 0;
	}

	/**
	 * 比較可能な値において、比較対象以上であることを検査します。
	 *
	 * @param <T>   検査する値
	 * @param other 比較対象（null不可）
	 * @return 検査関数
	 */
	public static <T extends Comparable<T>> Predicate<T> ge(T other) {
		return value -> value.compareTo(other) >= 0;
	}

	/**
	 * 比較可能な値において、範囲内（最小値以上、かつ最大値未満）であることを検査します。
	 *
	 * @param <T>          検査する値
	 * @param min          範囲最小値
	 * @param maxExclusive 範囲最大値（ただしこの値を含まない）
	 * @return 検査関数
	 */
	public static <T extends Comparable<T>> Predicate<T> range(T min, T maxExclusive) {
		return value -> value.compareTo(min) >= 0 && value.compareTo(maxExclusive) < 0;
	}

	/**
	 * 比較可能な値において、範囲外（最小値未満、または最大値以上）であることを検査します。
	 *
	 * @param <T>          検査する値
	 * @param min          範囲最小値
	 * @param maxExclusive 範囲最大値（ただしこの値を含まない）
	 * @return 検査関数
	 */
	public static <T extends Comparable<T>> Predicate<T> outOfRange(T min, T maxExclusive) {
		return range(min, maxExclusive).negate();
	}

	/**
	 * 配列の長さが範囲内（最小数以上、最大数以下）であることを検査します。
	 *
	 * @param min 最小数
	 * @param max 最大数
	 * @return 検査関数
	 */
	public static Predicate<Object[]> length(int min, int max) {
		return array -> array.length >= min && array.length <= max;
	}

	/**
	 * 値が、期待する値の中のひとつであることを検査します。
	 *
	 * @param <T>    検査する値
	 * @param others 期待する値
	 * @return 検査関数
	 */
	@SafeVarargs
	public static <T> Predicate<? super T> oneOf(T... others) {
		return value -> {
			var eq = eq(value);
			return Stream.of(others).anyMatch(eq);
		};
	}

	/**
	 * 値が、期待しない値の中のひとつではないことを検査します。
	 *
	 * @param <T>    検査する値
	 * @param others 期待しない値
	 * @return 検査関数
	 */
	@SafeVarargs
	public static <T> Predicate<? super T> notOneOf(T... others) {
		return oneOf(others).negate();
	}

	/**
	 * 複数の検査関数を実行できる検索オブジェクトを作成します。
	 *
	 * @param <T>    検査する値
	 * @param name   検査する値の名前
	 * @param target 検査する値
	 * @return 検索オブジェクト
	 */
	public static <T> Tester<T> of(String name, T target) {
		return new Tester<>(name, target);
	}

	/**
	 * 値をひとつの検査関数で検査します。
	 *
	 * <p>
	 * 値が null の場合は {@link NullPointerException} が、値が検査関数で false の場合は
	 * {@link IllegalArgumentException} がスローされます。
	 *
	 * @param <T>       検査する値
	 * @param name      検査する値の名前
	 * @param target    検査する値
	 * @param predicate 検査関数
	 * @return 検査した値
	 */
	public static <T> T must(String name, T target, Predicate<? super T> predicate) {
		return new Tester<>(name, target).must(predicate).get();
	}

	/** 検査する値の名前。 */
	private final String name;

	/** 検査する値。 */
	private final T target;

	private Tester(String name, T target) {
		this.name = name;
		this.target = target;
	}

	/**
	 * 値を検査関数で検査します。
	 *
	 * <p>
	 * 値が検査関数で false の場合は {@link IllegalArgumentException} がスローされます。なお、値が null
	 * の場合は検査されません。
	 *
	 * @param predicate 検査関数
	 * @return 検索オブジェクト
	 */
	public Tester<T> must(Predicate<? super T> predicate) {
		if (target != null && !predicate.test(target)) {
			throw new IllegalArgumentException(name);
		}

		return this;
	}

	/**
	 * 検査した値を取得します。
	 *
	 * <p>
	 * ただし、値が null の場合は {@link NullPointerException} をスローします。
	 *
	 * @return 検査した値
	 */
	public T get() {
		if (target == null) {
			throw new NullPointerException(name);
		}

		return target;
	}

	/**
	 * 検査した値を取得します。
	 *
	 * <p>
	 * ただし、値が null の場合は代替値を返却します。
	 *
	 * @param whenNull 値が null の場合に返却する代替値
	 * @return 検査した値
	 */
	public T orElse(T whenNull) {
		return (target != null) ? target : whenNull;
	}
}
