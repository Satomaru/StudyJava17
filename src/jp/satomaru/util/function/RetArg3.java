package jp.satomaru.util.function;

import jp.satomaru.util.Either;

/**
 * 引数みっつ戻り値ありの、例外をスローする関数です。
 *
 * @author Satomaru
 * @param <A1> 引数1
 * @param <A2> 引数2
 * @param <A3> 引数3
 * @param <R>  戻り値
 */
@FunctionalInterface
public interface RetArg3<A1, A2, A3, R> {

	/**
	 * 実行します。
	 *
	 * @param arg1 引数1
	 * @param arg2 引数2
	 * @param arg3 引数3
	 * @return 実行結果
	 * @throws Exception 異常が発生した場合
	 */
	R execute(A1 arg1, A2 arg2, A3 arg3) throws Exception;

	/**
	 * 引数1を注入します。
	 *
	 * @param arg1 引数1
	 * @return 注入後の関数
	 */
	default RetArg2<A2, A3, R> inject(A1 arg1) {
		return (arg2, arg3) -> execute(arg1, arg2, arg3);
	}

	/**
	 * 実行します。
	 *
	 * @param arg1 引数1
	 * @param arg2 引数2
	 * @param arg3 引数3
	 * @return 実行結果（非検査例外の場合は、そのままスローされます）
	 */
	default Either<Exception, R> run(A1 arg1, A2 arg2, A3 arg3) {
		return inject(arg1).inject(arg2).inject(arg3).run();
	}
}
