package jp.satomaru.util.function;

import java.util.Optional;

/**
 * 引数ふたつ戻り値なしの、例外をスローする関数です。
 *
 * @author Satomaru
 * @param <A1> 引数1
 * @param <A2> 引数2
 */
@FunctionalInterface
public interface VoidArg2<A1, A2> {

	/**
	 * 実行します。
	 *
	 * @param arg1 引数1
	 * @param arg2 引数2
	 * @throws Exception 異常が発生した場合
	 */
	void execute(A1 arg1, A2 arg2) throws Exception;

	/**
	 * 引数1を注入します。
	 *
	 * @param arg1 引数1
	 * @return 注入後の関数
	 */
	default VoidArg1<A2> inject(A1 arg1) {
		return arg2 -> execute(arg1, arg2);
	}

	/**
	 * 実行します。
	 *
	 * @param arg1 引数1
	 * @param arg2 引数2
	 * @return 発生した例外
	 */
	default Optional<Exception> run(A1 arg1, A2 arg2) {
		return inject(arg1).inject(arg2).run();
	}

	/**
	 * 実行後、引数1を返却する関数を作成します。
	 *
	 * @return 作成した関数
	 */
	default RetArg2<A1, A2, A1> retA1() {
		return (arg1, arg2) -> {
			execute(arg1, arg2);
			return arg1;
		};
	}

	/**
	 * 実行後、引数2を返却する関数を作成します。
	 *
	 * @return 作成した関数
	 */
	default RetArg2<A1, A2, A2> retA2() {
		return (arg1, arg2) -> {
			execute(arg1, arg2);
			return arg2;
		};
	}
}
