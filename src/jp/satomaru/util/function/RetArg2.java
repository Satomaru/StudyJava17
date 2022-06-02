package jp.satomaru.util.function;

import jp.satomaru.util.function.core.Arg2;

/**
 * 引数ふたつ戻り値ありの、例外をスローする関数です。
 *
 * @author Satomaru
 * @param <A1> 引数1
 * @param <A2> 引数2
 * @param <R>  戻り値
 */
@FunctionalInterface
public interface RetArg2<A1, A2, R> extends Arg2<A1, A2, R, RetArg1<A2, R>> {

	/**
	 * 実行します。
	 *
	 * @param arg1 引数1
	 * @param arg2 引数2
	 * @return 実行結果
	 * @throws Exception 異常が発生した場合
	 */
	R execute(A1 arg1, A2 arg2) throws Exception;

	@Override
	default RetArg1<A2, R> inject(A1 arg1) {
		return arg2 -> execute(arg1, arg2);
	}
}
