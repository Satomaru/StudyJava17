package jp.satomaru.util.function;

import jp.satomaru.util.function.core.Arg3;

/**
 * 引数みっつ戻り値なしの、例外をスローする関数です。
 *
 * @author Satomaru
 * @param <A1> 引数1
 * @param <A2> 引数2
 * @param <A3> 引数3
 */
@FunctionalInterface
public interface VoidArg3<A1, A2, A3> extends Arg3<A1, A2, A3, Void, RetArg2<A2, A3, Void>> {

	/**
	 * 実行します。
	 *
	 * @param arg1 引数1
	 * @param arg2 引数2
	 * @param arg3 引数3
	 * @throws Exception 異常が発生した場合
	 */
	void execute(A1 arg1, A2 arg2, A3 arg3) throws Exception;

	@Override
	default RetArg2<A2, A3, Void> inject(A1 arg1) {
		return (arg2, arg3) -> {
			execute(arg1, arg2, arg3);
			return null;
		};
	}
}
