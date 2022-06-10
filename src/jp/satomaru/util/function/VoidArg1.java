package jp.satomaru.util.function;

import java.util.Optional;

/**
 * 引数ひとつ戻り値なしの、例外をスローする関数です。
 *
 * @author Satomaru
 * @param <A1> 引数1
 */
@FunctionalInterface
public interface VoidArg1<A1> {

	/**
	 * 実行します。
	 *
	 * @param arg1 引数1
	 * @throws Exception 異常が発生した場合
	 */
	void execute(A1 arg1) throws Exception;

	/**
	 * 引数1を注入します。
	 *
	 * @param arg1 引数1
	 * @return 注入後の関数
	 */
	default VoidArg0 inject(A1 arg1) {
		return () -> execute(arg1);
	}

	/**
	 * 実行します。
	 *
	 * @param arg1 引数1
	 * @return 発生した例外
	 */
	default Optional<Exception> run(A1 arg1) {
		return inject(arg1).run();
	}

	/**
	 * 実行後、引数1を返却する関数を作成します。
	 *
	 * @return 作成した関数
	 */
	default RetArg1<A1, A1> retA1() {
		return arg1 -> {
			execute(arg1);
			return arg1;
		};
	}
}
