package jp.satomaru.util.either;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * �ӂ��̌^�̓��A�ǂ��炩��ێ����܂��B
 *
 * @author Satomaru
 * @param <L> ���̌^
 * @param <R> �E�̌^
 */
public sealed interface Either<L, R> permits EitherRight<L, R>, EitherLeft<L, R> {

	/**
	 * �ێ����Ă���l���擾���܂��B
	 *
	 * @return �ێ����Ă���l
	 */
	Object value();

	/**
	 * �E�̌^��ێ����Ă��邱�Ƃ𔻒肵�܂��B
	 *
	 * @return �E�̌^��ێ����Ă���ꍇ��true
	 */
	boolean isRight();

	/**
	 * ���̌^��ێ����Ă��邱�Ƃ𔻒肵�܂��B
	 *
	 * @return ���̌^��ێ����Ă���ꍇ��true
	 */
	default boolean isLeft() {
		return !isRight();
	}

	/**
	 * �ێ����Ă���l��ϊ����܂��B
	 *
	 * @param <T>       �ϊ���̌^
	 * @param whenLeft  ���̌^��ێ����Ă���ꍇ�Ɏg�p����֐�
	 * @param whenRight �E�̌^��ێ����Ă���ꍇ�Ɏg�p����֐�
	 * @return �ϊ���̒l
	 */
	default <T> T map(Function<L, T> whenLeft, Function<R, T> whenRight) {
		if (isRight()) {
			return whenRight.apply(rightValue());
		} else {
			return whenLeft.apply(leftValue());
		}
	}

	/**
	 * �E�̒l��ێ����Ă���ꍇ�A�w�肳�ꂽ�֐������s���܂��B
	 *
	 * @param whenRight �E�̌^��ێ����Ă���ꍇ�Ɏg�p����֐�
	 * @return ���̃I�u�W�F�N�g���g
	 */
	default Either<L, R> ifRight(Consumer<R> whenRight) {
		if (isRight()) {
			whenRight.accept(rightValue());
		}

		return this;
	}

	/**
	 * ���̒l��ێ����Ă���ꍇ�A�w�肳�ꂽ�֐������s���܂��B
	 *
	 * @param whenLeft ���̌^��ێ����Ă���ꍇ�Ɏg�p����֐�
	 * @return ���̃I�u�W�F�N�g���g
	 */
	default Either<L, R> ifLeft(Consumer<L> whenLeft) {
		if (isLeft()) {
			whenLeft.accept(leftValue());
		}

		return this;
	}

	private R rightValue() {
		if (!isRight()) {
			throw new IllegalStateException();
		}

		@SuppressWarnings("unchecked")
		var right = (R) value();

		return right;
	}

	private L leftValue() {
		if (!isLeft()) {
			throw new IllegalStateException();
		}

		@SuppressWarnings("unchecked")
		var left = (L) value();

		return left;
	}
}
