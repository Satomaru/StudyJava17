package jp.satomaru.util.function;

import jp.satomaru.util.either.Either;
import jp.satomaru.util.either.EitherLeft;
import jp.satomaru.util.either.EitherRight;
import jp.satomaru.util.function.core.Arg0;

/**
 * 引数なし戻り値なしの、例外をスローする関数です。
 *
 * @author Satomaru
 */
@FunctionalInterface
public interface VoidArg0 extends Arg0<Void> {

	/**
	 * 実行します。
	 *
	 * @throws Exception 異常が発生した場合
	 */
	void execute() throws Exception;

	@Override
	default Either<Exception, Void> run() {
		try {
			execute();
			return new EitherRight<>(null);
		} catch (Exception e) {
			return new EitherLeft<>(e);
		}
	}
}
