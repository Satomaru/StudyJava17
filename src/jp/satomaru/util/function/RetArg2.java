package jp.satomaru.util.function;

import jp.satomaru.util.Either;

/**
 * 引数ふたつ戻り値ありの、例外をスローする関数です。
 *
 * @author Satomaru
 * @param <A1> 引数1
 * @param <A2> 引数2
 * @param <R>  戻り値
 */
@FunctionalInterface
public interface RetArg2<A1, A2, R> {

	/**
	 * 実行します。
	 *
	 * @param arg1 引数1
	 * @param arg2 引数2
	 * @return 実行結果
	 * @throws Exception 異常が発生した場合
	 */
	R execute(A1 arg1, A2 arg2) throws Exception;

	/**
	 * 引数1を注入します。
	 *
	 * @param arg1 引数1
	 * @return 注入後の関数
	 */
	default RetArg1<A2, R> inject(A1 arg1) {
		return arg2 -> execute(arg1, arg2);
	}

	/**
	 * 実行します。
	 *
	 * @param arg1 引数1
	 * @param arg2 引数2
	 * @return 実行結果（非検査例外の場合は、そのままスローされます）
	 */
	default Either<Exception, R> run(A1 arg1, A2 arg2) {
		return inject(arg1).inject(arg2).run();
	}
}
