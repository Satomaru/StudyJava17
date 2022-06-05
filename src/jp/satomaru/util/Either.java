package jp.satomaru.util;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import jp.satomaru.util.Either.Left;
import jp.satomaru.util.Either.Right;

/**
 * �ӂ��̌^�̓��̂ǂ��炩��ێ����܂��B
 *
 * @author Satomaru
 * @param <L> ���̌^
 * @param <R> �E�̌^
 */
public sealed interface Either<L, R> permits Left<L, R>, Right<L, R> {

	/**
	 * ���̌^��ێ����܂��B
	 *
	 * @param <L> ���̌^
	 * @param <R> �E�̌^
	 */
	public record Left<L, R> (L value) implements Either<L, R> {
		@Override
		public Optional<L> optional() {
			return Optional.ofNullable(value);
		}
	}

	/**
	 * �E�̌^��ێ����܂��B
	 *
	 * @param <L> ���̌^
	 * @param <R> �E�̌^
	 */
	public record Right<L, R> (R value) implements Either<L, R> {
		@Override
		public Optional<R> optional() {
			return Optional.ofNullable(value);
		}
	}

	/**
	 * �ێ����Ă���l���擾���܂��B
	 *
	 * @return �ێ����Ă���l
	 */
	Object value();

	/**
	 * �ێ����Ă���l���擾���܂��B
	 *
	 * @return �ێ����Ă���l
	 */
	Optional<?> optional();

	/**
	 * ���̌^��ێ����Ă��邱�Ƃ𔻒肵�܂��B
	 *
	 * @return ���̌^��ێ����Ă���ꍇ��true
	 */
	default boolean isLeft() {
		return this instanceof Left;
	}

	/**
	 * �E�̌^��ێ����Ă��邱�Ƃ𔻒肵�܂��B
	 *
	 * @return �E�̌^��ێ����Ă���ꍇ��true
	 */
	default boolean isRight() {
		return this instanceof Right;
	}

	/**
	 * ���̒l��ێ����Ă���ꍇ�A�l���擾���܂��B
	 *
	 * @return �ێ����Ă���l
	 */
	default Optional<L> optionalLeft() {
		if (this instanceof Left<L, R> left) {
			return left.optional();
		}

		return Optional.empty();
	}

	/**
	 * �E�̒l��ێ����Ă���ꍇ�A�l���擾���܂��B
	 *
	 * @return �ێ����Ă���l
	 */
	default Optional<R> optionalRight() {
		if (this instanceof Right<L, R> right) {
			return right.optional();
		}

		return Optional.empty();
	}

	/**
	 * �ێ����Ă���l��ϊ����܂��B
	 *
	 * @param <T>       �ϊ���
	 * @param whenLeft  ���̌^��ێ����Ă���ꍇ�Ɏg�p����֐�
	 * @param whenRight �E�̌^��ێ����Ă���ꍇ�Ɏg�p����֐�
	 * @return �ϊ���̒l
	 */
	default <T> Optional<T> map(
		Function<? super L, ? extends T> whenLeft,
		Function<? super R, ? extends T> whenRight) {

		if (this instanceof Left<L, R> left) {
			return left.optional().map(whenLeft);
		}

		Right<L, R> right = (Right<L, R>) this;
		return right.optional().map(whenRight);
	}

	/**
	 * �ێ����Ă���l�𕶎���ɕϊ����܂��B
	 *
	 * @return �ϊ���̒l
	 */
	default Optional<String> mapToString() {
		return optional().map(Object::toString);
	}

	/**
	 * ���̒l��ێ����Ă��āA���l��null�ł͂Ȃ��ꍇ�A�w�肳�ꂽ�֐������s���܂��B
	 *
	 * @param action ���s����֐�
	 * @return ���̃I�u�W�F�N�g���g
	 */
	default Either<L, R> ifPresentLeft(Consumer<? super L> action) {
		optionalLeft().ifPresent(action);
		return this;
	}

	/**
	 * �E�̒l��ێ����Ă��āA���l��null�ł͂Ȃ��ꍇ�A�w�肳�ꂽ�֐������s���܂��B
	 *
	 * @param action ���s����֐�
	 * @return ���̃I�u�W�F�N�g���g
	 */
	default Either<L, R> ifPresentRight(Consumer<? super R> action) {
		optionalRight().ifPresent(action);
		return this;
	}
}
