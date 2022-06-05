package jp.satomaru.util.function;

import java.util.Optional;

/**
 * 引数なし戻り値なしの、例外をスローする関数です。
 *
 * @author Satomaru
 */
@FunctionalInterface
public interface VoidArg0 {

	/**
	 * 実行します。
	 *
	 * @throws Exception 異常が発生した場合
	 */
	void execute() throws Exception;

	/**
	 * 実行します。
	 *
	 * @return 発生した例外
	 */
	default Optional<Exception> run() {
		try {
			execute();
			return Optional.empty();
		} catch (Exception e) {
			return Optional.of(e);
		}
	}
}
