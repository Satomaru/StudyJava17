package jp.satomaru.util.either;

/**
 * 右の型を保持します。
 *
 * @author Satomaru
 * @param <L> 左の型
 * @param <R> 右の型
 */
public record EitherRight<L, R> (R value) implements Either<L, R> {

	@Override
	public boolean isRight() {
		return true;
	}
}
