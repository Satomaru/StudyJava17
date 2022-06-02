package jp.satomaru.util.function.core;

import jp.satomaru.util.either.Either;

/**
 * 引数なしの関数に、Eitherをサポートさせます。
 *
 * @author Satomaru
 * @param <R> 戻り値
 */
public interface Arg0<R> {

	/**
	 * 関数を実行し、結果または例外を取得します。
	 *
	 * @return 結果または例外
	 */
	Either<Exception, R> run();
}
