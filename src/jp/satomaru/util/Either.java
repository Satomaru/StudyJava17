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
		public boolean isLeft() {
			return true;
		}

		@Override
		public Optional<L> optional() {
			return Optional.ofNullable(value);
		}

		@Override
		public Optional<L> optionalLeft() {
			return Optional.ofNullable(value);
		}

		@Override
		public Optional<R> optionalRight() {
			return Optional.empty();
		}

		@Override
		public <E extends Exception> R orElseThrow(Function<L, E> generator) throws E {
			throw generator.apply(value);
		}

		@Override
		public <T> Optional<T> map(Function<? super L, T> whenLeft, Function<? super R, T> whenRight) {
			return optional().map(whenLeft);
		}

		@Override
		public <L2> Either<L2, R> mapLeft(Function<L, L2> mapper) {
			return new Left<>(mapper.apply(value));
		}

		@Override
		public <R2> Either<L, R2> mapRight(Function<R, R2> mapper) {
			return new Left<>(value);
		}

		@Override
		public Either<L, R> ifPresentLeft(Consumer<? super L> action) {
			optional().ifPresent(action);
			return this;
		}

		@Override
		public Either<L, R> ifPresentRight(Consumer<? super R> action) {
			return this;
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
		public boolean isLeft() {
			return false;
		}

		@Override
		public Optional<R> optional() {
			return Optional.ofNullable(value);
		}

		@Override
		public Optional<L> optionalLeft() {
			return Optional.empty();
		}

		@Override
		public Optional<R> optionalRight() {
			return Optional.ofNullable(value);
		}

		@Override
		public <E extends Exception> R orElseThrow(Function<L, E> generator) throws E {
			return value;
		}

		@Override
		public <T> Optional<T> map(Function<? super L, T> whenLeft, Function<? super R, T> whenRight) {
			return optional().map(whenRight);
		}

		@Override
		public <L2> Either<L2, R> mapLeft(Function<L, L2> mapper) {
			return new Right<>(value);
		}

		@Override
		public <R2> Either<L, R2> mapRight(Function<R, R2> mapper) {
			return new Right<>(mapper.apply(value));
		}

		@Override
		public Either<L, R> ifPresentLeft(Consumer<? super L> action) {
			return this;
		}

		@Override
		public Either<L, R> ifPresentRight(Consumer<? super R> action) {
			optional().ifPresent(action);
			return this;
		}
	}

	/**
	 * 保持している値。
	 */
	Object value();

	/**
	 * 左の型を保持していることを判定します。
	 *
	 * @return 左の型を保持している場合はtrue
	 */
	boolean isLeft();

	/**
	 * 保持している値を取得します。
	 *
	 * @return 保持している値
	 */
	Optional<?> optional();

	/**
	 * 左の値を保持している場合、値を取得します。
	 *
	 * @return 保持している値
	 */
	Optional<L> optionalLeft();

	/**
	 * 右の値を保持している場合、値を取得します。
	 *
	 * @return 保持している値
	 */
	Optional<R> optionalRight();

	/**
	 * 保持している値が、右の型の場合は返却し、左の型の場合はスローします。
	 *
	 * @param <E>       スローする例外
	 * @param generator 左の型から例外を生成する関数
	 * @return 右の値
	 * @throws E 左の値を保持していた場合
	 */
	<E extends Exception> R orElseThrow(Function<L, E> generator) throws E;

	/**
	 * 保持している値を変換します。
	 *
	 * @param <T>       変換後
	 * @param whenLeft  左の型を保持している場合に使用する関数
	 * @param whenRight 右の型を保持している場合に使用する関数
	 * @return 変換後の値
	 */
	<T> Optional<T> map(Function<? super L, T> whenLeft, Function<? super R, T> whenRight);

	/**
	 * 左の値を変換します。
	 *
	 * @param <L2>   新しい左の値
	 * @param mapper 変換する関数
	 * @return 変換後のオブジェクト
	 */
	<L2> Either<L2, R> mapLeft(Function<L, L2> mapper);

	/**
	 * 右の値を変換します。
	 *
	 * @param <R2>   新しい右の値
	 * @param mapper 変換する関数
	 * @return 変換後のオブジェクト
	 */
	<R2> Either<L, R2> mapRight(Function<R, R2> mapper);

	/**
	 * 左の値を保持していて、かつ値がnullではない場合、指定された関数を実行します。
	 *
	 * @param action 実行する関数
	 * @return このオブジェクト自身
	 */
	Either<L, R> ifPresentLeft(Consumer<? super L> action);

	/**
	 * 右の値を保持していて、かつ値がnullではない場合、指定された関数を実行します。
	 *
	 * @param action 実行する関数
	 * @return このオブジェクト自身
	 */
	Either<L, R> ifPresentRight(Consumer<? super R> action);

	/**
	 * 右の型を保持していることを判定します。
	 *
	 * @return 右の型を保持している場合はtrue
	 */
	default boolean isRight() {
		return !isLeft();
	}

	/**
	 * 保持している値が、右の型の場合は返却し、左の型の場合は実行時例外をスローします。
	 *
	 * @param <E>              実行時例外
	 * @param exceptionWrapper 左の型が例外だった場合に実行時例外を作成する関数
	 * @param stringWrapper    左の型が例外ではなかった場合に実行時例外を作成する関数
	 * @return 右の値
	 */
	default <E extends RuntimeException> R orElseThrowRuntime(
		Function<Exception, E> exceptionWrapper,
		Function<String, E> stringWrapper) {

		return orElseThrow(leftValue -> {
			if (leftValue == null) {
				throw new NullPointerException();
			}

			if (leftValue instanceof RuntimeException runtime) {
				throw runtime;
			}

			if (leftValue instanceof Exception exception) {
				throw exceptionWrapper.apply(exception);
			}

			throw stringWrapper.apply(leftValue.toString());
		});
	}

	/**
	 * 保持している値が、右の型の場合は返却し、左の型の場合は不正引数例外をスローします。
	 *
	 * @return 右の値
	 */
	default R orElseIllegalArgument() {
		return orElseThrowRuntime(
			IllegalArgumentException::new,
			IllegalArgumentException::new);
	}

	/**
	 * 保持している値が、右の型の場合は返却し、左の型の場合は不正状態例外をスローします。
	 *
	 * @return 右の値
	 */
	default R orElseIllegalState() {
		return orElseThrowRuntime(
			IllegalStateException::new,
			IllegalStateException::new);
	}

	/**
	 * 保持している値を文字列に変換します。
	 *
	 * @return 文字列
	 */
	default Optional<String> mapToString() {
		return optional().map(Object::toString);
	}

	/**
	 * 保持している値を文字列に編集します。
	 *
	 * @param format フォーマット ({@link java.util.Formatter}と同じ)
	 * @return 文字列
	 */
	default Optional<String> format(String format) {
		return optional().map(value -> String.format(format, value));
	}
}
