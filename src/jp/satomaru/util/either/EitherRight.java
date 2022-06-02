package jp.satomaru.util.either;

/**
 * �E�̌^��ێ����܂��B
 *
 * @author Satomaru
 * @param <L> ���̌^
 * @param <R> �E�̌^
 */
public record EitherRight<L, R> (R value) implements Either<L, R> {

	@Override
	public boolean isRight() {
		return true;
	}
}
