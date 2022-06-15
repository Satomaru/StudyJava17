package jp.satomaru.util.function;

import java.util.function.Function;

import jp.satomaru.util.Either;

/**
 * {@link Function} に相当するけど、例外をスローする関数です。
 *
 * @author Satomaru
 *
 * @param <T> 引数
 * @param <R> 戻り値
 * @param <E> 例外
 */
@FunctionalInterface
public interface ThrowableFunction<T, R, E extends Exception> {

	/**
	 * 実行します。
	 *
	 * @param arg 引数
	 * @return 実行結果
	 * @throws E 例外が発生した場合
	 */
	R apply(T arg) throws E;

	/**
	 * 実行します。
	 *
	 * @param thrown Eitherに格納される例外の型（非検査例外は不可）
	 * @param arg    引数
	 * @return 実行結果（非検査例外の場合は、そのままスローされます）
	 */
	default Either<E, R> run(Class<E> thrown, T arg) {
		if (RuntimeException.class.isAssignableFrom(thrown)) {
			throw new IllegalArgumentException("thrown should not be an unchecked exception");
		}

		try {
			return new Either.Right<>(apply(arg));
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			return new Either.Left<>(thrown.cast(e));
		}
	}

	/**
	 * Functionに変換します。
	 *
	 * @param thrown Eitherに格納される例外の型（非検査例外は不可）
	 * @return 変換されたFunction（非検査例外の場合は、そのままスローされます）
	 */
	default Function<T, Either<E, R>> function(Class<E> thrown) {
		if (RuntimeException.class.isAssignableFrom(thrown)) {
			throw new IllegalArgumentException("thrown should not be an unchecked exception");
		}

		return arg -> run(thrown, arg);
	}
}
