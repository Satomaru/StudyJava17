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
	 * @param arg 引数
	 * @return 実行結果（非検査例外の場合は、そのままスローされます）
	 */
	default Either<E, R> run(T arg) {
		try {
			return new Either.Right<>(apply(arg));
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			@SuppressWarnings("unchecked")
			E left = (E) e;

			return new Either.Left<>(left);
		}
	}
}
