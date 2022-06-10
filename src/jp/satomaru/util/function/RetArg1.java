package jp.satomaru.util.function;

import jp.satomaru.util.Either;

/**
 * 引数ひとつ戻り値ありの、例外をスローする関数です。
 *
 * @author Satomaru
 * @param <A1> 引数1
 * @param <R>  戻り値
 */
@FunctionalInterface
public interface RetArg1<A1, R> {

	/**
	 * 実行します。
	 *
	 * @param arg1 引数1
	 * @return 実行結果
	 * @throws Exception 異常が発生した場合
	 */
	R execute(A1 arg1) throws Exception;

	/**
	 * 引数1を注入します。
	 *
	 * @param arg1 引数1
	 * @return 注入後の関数
	 */
	default RetArg0<R> inject(A1 arg1) {
		return () -> execute(arg1);
	}

	/**
	 * 実行します。
	 *
	 * @param arg1 引数1
	 * @return 実行結果
	 */
	default Either<Exception, R> run(A1 arg1) {
		return inject(arg1).run();
	}
}
