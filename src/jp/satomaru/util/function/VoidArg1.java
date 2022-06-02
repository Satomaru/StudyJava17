package jp.satomaru.util.function;

import jp.satomaru.util.function.core.Arg1;

/**
 * 引数ひとつ戻り値なしの、例外をスローする関数です。
 *
 * @author Satomaru
 * @param <A1> 引数1
 */
@FunctionalInterface
public interface VoidArg1<A1> extends Arg1<A1, Void, RetArg0<Void>> {

	/**
	 * 実行します。
	 *
	 * @param arg1 引数1
	 * @throws Exception 異常が発生した場合
	 */
	void execute(A1 arg1) throws Exception;

	@Override
	default RetArg0<Void> inject(A1 arg1) {
		return () -> {
			execute(arg1);
			return null;
		};
	}
}
