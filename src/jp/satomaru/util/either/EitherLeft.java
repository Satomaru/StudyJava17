package jp.satomaru.util.either;

/**
 * ���̌^��ێ����܂��B
 *
 * @author Satomaru
 * @param <L> ���̌^
 * @param <R> �E�̌^
 */
public record EitherLeft<L, R> (L value) implements Either<L, R> {

	@Override
	public boolean isRight() {
		return false;
	}
}
