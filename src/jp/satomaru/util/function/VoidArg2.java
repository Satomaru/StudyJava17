package jp.satomaru.util.function;

import jp.satomaru.util.function.core.Arg2;

/**
 * 引数ふたつ戻り値なしの、例外をスローする関数です。
 *
 * @author Satomaru
 * @param <A1> 引数1
 * @param <A2> 引数2
 */
@FunctionalInterface
public interface VoidArg2<A1, A2> extends Arg2<A1, A2, Void, RetArg1<A2, Void>> {

	/**
	 * 実行します。
	 *
	 * @param arg1 引数1
	 * @param arg2 引数2
	 * @throws Exception 異常が発生した場合
	 */
	void execute(A1 arg1, A2 arg2) throws Exception;

	@Override
	default RetArg1<A2, Void> inject(A1 arg1) {
		return arg2 -> {
			execute(arg1, arg2);
			return null;
		};
	}
}
