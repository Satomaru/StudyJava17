package jp.satomaru.util.function;

import jp.satomaru.util.Either;

/**
 * 引数なし戻り値ありの、例外をスローする関数です。
 *
 * @author Satomaru
 * @param <R> 戻り値
 */
@FunctionalInterface
public interface RetArg0<R> {

	/**
	 * 実行します。
	 *
	 * @return 実行結果
	 * @throws Exception 異常が発生した場合
	 */
	R execute() throws Exception;

	/**
	 * 実行します。
	 *
	 * @return 実行結果（非検査例外の場合は、そのままスローされます）
	 */
	default Either<Exception, R> run() {
		try {
			return new Either.Right<>(execute());
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			return new Either.Left<>(e);
		}
	}
}
