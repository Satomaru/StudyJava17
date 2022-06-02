package jp.satomaru.util.function.core;

import jp.satomaru.util.either.Either;

/**
 * 引数ふたつの関数に、Eitherをサポートさせます。
 *
 * @author Satomaru
 * @param <A1> 引数1
 * @param <A2> 引数2
 * @param <R>  戻り値
 * @param <I>  引数1を注入した後の関数
 */
public interface Arg2<A1, A2, R, I extends Arg1<A2, R, ?>> {

	/**
	 * 引数1を注入します。
	 *
	 * @param arg1 引数1
	 * @return 注入後の関数
	 */
	I inject(A1 arg1);

	/**
	 * 関数を実行し、結果または例外を取得します。
	 *
	 * @param arg1 引数1
	 * @param arg2 引数2
	 * @return 結果または例外
	 */
	default Either<Exception, R> run(A1 arg1, A2 arg2) {
		return inject(arg1).inject(arg2).run();
	}
}
