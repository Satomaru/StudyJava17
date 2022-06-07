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
		public boolean isLeft() {
			return true;
		}

		@Override
		public Optional<L> optional() {
			return Optional.ofNullable(value);
		}

		@Override
		public Optional<L> optionalLeft() {
			return Optional.ofNullable(value);
		}

		@Override
		public Optional<R> optionalRight() {
			return Optional.empty();
		}

		@Override
		public <E extends Exception> R orElseThrow(Function<L, E> generator) throws E {
			throw generator.apply(value);
		}

		@Override
		public <T> Optional<T> map(Function<? super L, T> whenLeft, Function<? super R, T> whenRight) {
			return optional().map(whenLeft);
		}

		@Override
		public Either<L, R> ifPresentLeft(Consumer<? super L> action) {
			optional().ifPresent(action);
			return this;
		}

		@Override
		public Either<L, R> ifPresentRight(Consumer<? super R> action) {
			return this;
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
		public boolean isLeft() {
			return false;
		}

		@Override
		public Optional<R> optional() {
			return Optional.ofNullable(value);
		}

		@Override
		public Optional<L> optionalLeft() {
			return Optional.empty();
		}

		@Override
		public Optional<R> optionalRight() {
			return Optional.ofNullable(value);
		}

		@Override
		public <E extends Exception> R orElseThrow(Function<L, E> generator) throws E {
			return value;
		}

		@Override
		public <T> Optional<T> map(Function<? super L, T> whenLeft, Function<? super R, T> whenRight) {
			return optional().map(whenRight);
		}

		@Override
		public Either<L, R> ifPresentLeft(Consumer<? super L> action) {
			return this;
		}

		@Override
		public Either<L, R> ifPresentRight(Consumer<? super R> action) {
			optional().ifPresent(action);
			return this;
		}
	}

	/**
	 * �ێ����Ă���l���擾���܂��B
	 *
	 * @return �ێ����Ă���l
	 */
	Object value();

	/**
	 * ���̌^��ێ����Ă��邱�Ƃ𔻒肵�܂��B
	 *
	 * @return ���̌^��ێ����Ă���ꍇ��true
	 */
	boolean isLeft();

	/**
	 * �ێ����Ă���l���擾���܂��B
	 *
	 * @return �ێ����Ă���l
	 */
	Optional<?> optional();

	/**
	 * ���̒l��ێ����Ă���ꍇ�A�l���擾���܂��B
	 *
	 * @return �ێ����Ă���l
	 */
	Optional<L> optionalLeft();

	/**
	 * �E�̒l��ێ����Ă���ꍇ�A�l���擾���܂��B
	 *
	 * @return �ێ����Ă���l
	 */
	Optional<R> optionalRight();

	/**
	 * �ێ����Ă���l���A�E�̌^�̏ꍇ�͕ԋp���A���̌^�̏ꍇ�̓X���[���܂��B
	 *
	 * @param <E>       �X���[�����O
	 * @param generator ���̌^�����O�𐶐�����֐�
	 * @return �E�̒l
	 * @throws E ���̒l��ێ����Ă����ꍇ
	 */
	<E extends Exception> R orElseThrow(Function<L, E> generator) throws E;

	/**
	 * �ێ����Ă���l��ϊ����܂��B
	 *
	 * @param <T>       �ϊ���
	 * @param whenLeft  ���̌^��ێ����Ă���ꍇ�Ɏg�p����֐�
	 * @param whenRight �E�̌^��ێ����Ă���ꍇ�Ɏg�p����֐�
	 * @return �ϊ���̒l
	 */
	<T> Optional<T> map(Function<? super L, T> whenLeft, Function<? super R, T> whenRight);

	/**
	 * ���̒l��ێ����Ă��āA���l��null�ł͂Ȃ��ꍇ�A�w�肳�ꂽ�֐������s���܂��B
	 *
	 * @param action ���s����֐�
	 * @return ���̃I�u�W�F�N�g���g
	 */
	Either<L, R> ifPresentLeft(Consumer<? super L> action);

	/**
	 * �E�̒l��ێ����Ă��āA���l��null�ł͂Ȃ��ꍇ�A�w�肳�ꂽ�֐������s���܂��B
	 *
	 * @param action ���s����֐�
	 * @return ���̃I�u�W�F�N�g���g
	 */
	Either<L, R> ifPresentRight(Consumer<? super R> action);

	/**
	 * �E�̌^��ێ����Ă��邱�Ƃ𔻒肵�܂��B
	 *
	 * @return �E�̌^��ێ����Ă���ꍇ��true
	 */
	default boolean isRight() {
		return !isLeft();
	}

	/**
	 * �ێ����Ă���l���A�E�̌^�̏ꍇ�͕ԋp���A���̌^�̏ꍇ�͎��s����O���X���[���܂��B
	 *
	 * @param <E>              ���s����O
	 * @param exceptionWrapper ���̌^����O�������ꍇ�Ɏ��s����O���쐬����֐�
	 * @param stringWrapper    ���̌^����O�ł͂Ȃ������ꍇ�Ɏ��s����O���쐬����֐�
	 * @return �E�̒l
	 */
	default <E extends RuntimeException> R orElseThrowRuntime(
		Function<Exception, E> exceptionWrapper,
		Function<String, E> stringWrapper) {

		return orElseThrow(leftValue -> {
			if (leftValue == null) {
				throw new NullPointerException();
			}

			if (leftValue instanceof RuntimeException runtime) {
				throw runtime;
			}

			if (leftValue instanceof Exception exception) {
				throw exceptionWrapper.apply(exception);
			}

			throw stringWrapper.apply(leftValue.toString());
		});
	}

	/**
	 * �ێ����Ă���l���A�E�̌^�̏ꍇ�͕ԋp���A���̌^�̏ꍇ�͕s��������O���X���[���܂��B
	 *
	 * @return �E�̒l
	 */
	default R orElseIllegalArgument() {
		return orElseThrowRuntime(
			IllegalArgumentException::new,
			IllegalArgumentException::new);
	}

	/**
	 * �ێ����Ă���l���A�E�̌^�̏ꍇ�͕ԋp���A���̌^�̏ꍇ�͕s����ԗ�O���X���[���܂��B
	 *
	 * @return �E�̒l
	 */
	default R orElseIllegalState() {
		return orElseThrowRuntime(
			IllegalStateException::new,
			IllegalStateException::new);
	}

	/**
	 * �ێ����Ă���l�𕶎���ɕϊ����܂��B
	 *
	 * @return ������
	 */
	default Optional<String> mapToString() {
		return optional().map(Object::toString);
	}

	/**
	 * �ێ����Ă���l�𕶎���ɕҏW���܂��B
	 *
	 * @param format �t�H�[�}�b�g
	 * @return ������
	 */
	default Optional<String> format(String format) {
		return optional().map(value -> String.format(format, value));
	}
}
