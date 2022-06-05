package jp.satomaru.util;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import jp.satomaru.util.Either.Left;
import jp.satomaru.util.Either.Right;

/**
 * ふたつの型の内のどちらかを保持します。
 *
 * @author Satomaru
 * @param <L> 左の型
 * @param <R> 右の型
 */
public sealed interface Either<L, R> permits Left<L, R>, Right<L, R> {

	/**
	 * 左の型を保持します。
	 *
	 * @param <L> 左の型
	 * @param <R> 右の型
	 */
	public record Left<L, R> (L value) implements Either<L, R> {
		@Override
		public Optional<L> optional() {
			return Optional.ofNullable(value);
		}
	}

	/**
	 * 右の型を保持します。
	 *
	 * @param <L> 左の型
	 * @param <R> 右の型
	 */
	public record Right<L, R> (R value) implements Either<L, R> {
		@Override
		public Optional<R> optional() {
			return Optional.ofNullable(value);
		}
	}

	/**
	 * 保持している値を取得します。
	 *
	 * @return 保持している値
	 */
	Object value();

	/**
	 * 保持している値を取得します。
	 *
	 * @return 保持している値
	 */
	Optional<?> optional();

	/**
	 * 左の型を保持していることを判定します。
	 *
	 * @return 左の型を保持している場合はtrue
	 */
	default boolean isLeft() {
		return this instanceof Left;
	}

	/**
	 * 右の型を保持していることを判定します。
	 *
	 * @return 右の型を保持している場合はtrue
	 */
	default boolean isRight() {
		return this instanceof Right;
	}

	/**
	 * 左の値を保持している場合、値を取得します。
	 *
	 * @return 保持している値
	 */
	default Optional<L> optionalLeft() {
		if (this instanceof Left<L, R> left) {
			return left.optional();
		}

		return Optional.empty();
	}

	/**
	 * 右の値を保持している場合、値を取得します。
	 *
	 * @return 保持している値
	 */
	default Optional<R> optionalRight() {
		if (this instanceof Right<L, R> right) {
			return right.optional();
		}

		return Optional.empty();
	}

	/**
	 * 保持している値を変換します。
	 *
	 * @param <T>       変換後
	 * @param whenLeft  左の型を保持している場合に使用する関数
	 * @param whenRight 右の型を保持している場合に使用する関数
	 * @return 変換後の値
	 */
	default <T> Optional<T> map(
		Function<? super L, ? extends T> whenLeft,
		Function<? super R, ? extends T> whenRight) {

		if (this instanceof Left<L, R> left) {
			return left.optional().map(whenLeft);
		}

		Right<L, R> right = (Right<L, R>) this;
		return right.optional().map(whenRight);
	}

	/**
	 * 保持している値を文字列に変換します。
	 *
	 * @return 変換後の値
	 */
	default Optional<String> mapToString() {
		return optional().map(Object::toString);
	}

	/**
	 * 左の値を保持していて、かつ値がnullではない場合、指定された関数を実行します。
	 *
	 * @param action 実行する関数
	 * @return このオブジェクト自身
	 */
	default Either<L, R> ifPresentLeft(Consumer<? super L> action) {
		optionalLeft().ifPresent(action);
		return this;
	}

	/**
	 * 右の値を保持していて、かつ値がnullではない場合、指定された関数を実行します。
	 *
	 * @param action 実行する関数
	 * @return このオブジェクト自身
	 */
	default Either<L, R> ifPresentRight(Consumer<? super R> action) {
		optionalRight().ifPresent(action);
		return this;
	}
}
