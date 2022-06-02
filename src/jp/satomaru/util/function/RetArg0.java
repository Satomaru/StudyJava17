package jp.satomaru.util.function;

import jp.satomaru.util.either.Either;
import jp.satomaru.util.either.EitherLeft;
import jp.satomaru.util.either.EitherRight;
import jp.satomaru.util.function.core.Arg0;

/**
 * 引数なし戻り値ありの、例外をスローする関数です。
 *
 * @author Satomaru
 * @param <R> 戻り値
 */
@FunctionalInterface
public interface RetArg0<R> extends Arg0<R> {

	/**
	 * 実行します。
	 *
	 * @return 実行結果
	 * @throws Exception 異常が発生した場合
	 */
	R execute() throws Exception;

	@Override
	default Either<Exception, R> run() {
		try {
			return new EitherRight<>(execute());
		} catch (Exception e) {
			return new EitherLeft<>(e);
		}
	}
}
