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
		public <L2> Either<L2, R> mapLeft(Function<L, L2> mapper) {
			return new Left<>(mapper.apply(value));
		}

		@Override
		public <R2> Either<L, R2> mapRight(Function<R, R2> mapper) {
			return new Left<>(value);
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
		public <L2> Either<L2, R> mapLeft(Function<L, L2> mapper) {
			return new Right<>(value);
		}

		@Override
		public <R2> Either<L, R2> mapRight(Function<R, R2> mapper) {
			return new Right<>(mapper.apply(value));
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
	 * @see #optional()
	 * @see #optionalLeft()
	 * @see #optionalRight()
	 */
	Object value();

	/**
	 * ���̌^��ێ����Ă��邱�Ƃ𔻒肵�܂��B
	 *
	 * @return ���̌^��ێ����Ă���ꍇ��true
	 * @see #isRight()
	 */
	boolean isLeft();

	/**
	 * �ێ����Ă���l���擾���܂��B
	 *
	 * @return �ێ����Ă���l
	 * @see #value()
	 * @see #optionalLeft()
	 * @see #optionalRight()
	 */
	Optional<?> optional();

	/**
	 * ���̒l��ێ����Ă���ꍇ�A�l���擾���܂��B
	 *
	 * @return �ێ����Ă���l
	 * @see #value()
	 * @see #optional()
	 * @see #optionalRight()
	 */
	Optional<L> optionalLeft();

	/**
	 * �E�̒l��ێ����Ă���ꍇ�A�l���擾���܂��B
	 *
	 * @return �ێ����Ă���l
	 * @see #value()
	 * @see #optional()
	 * @see #optionalLeft()
	 */
	Optional<R> optionalRight();

	/**
	 * �ێ����Ă���l���A�E�̌^�̏ꍇ�͕ԋp���A���̌^�̏ꍇ�̓X���[���܂��B
	 *
	 * @param <E>       �X���[�����O
	 * @param generator ���̌^�����O�𐶐�����֐�
	 * @return �E�̒l
	 * @throws E ���̒l��ێ����Ă����ꍇ
	 * @see #orElseThrowRuntime(Function, Function)
	 * @see #orElseIllegalArgument()
	 * @see #orElseIllegalState()
	 */
	<E extends Exception> R orElseThrow(Function<L, E> generator) throws E;

	/**
	 * �ێ����Ă���l��ϊ����܂��B
	 *
	 * @param <T>       �ϊ���
	 * @param whenLeft  ���̌^��ێ����Ă���ꍇ�Ɏg�p����֐�
	 * @param whenRight �E�̌^��ێ����Ă���ꍇ�Ɏg�p����֐�
	 * @return �ϊ���̒l
	 * @see #mapLeft(Function)
	 * @see #mapRight(Function)
	 * @see #mapToString()
	 * @see #format(String)
	 */
	<T> Optional<T> map(Function<? super L, T> whenLeft, Function<? super R, T> whenRight);

	/**
	 * ���̒l��ϊ����܂��B
	 *
	 * @param <L2>   �V�������̒l
	 * @param mapper �ϊ�����֐�
	 * @return �ϊ���̃I�u�W�F�N�g
	 * @see #map(Function, Function)
	 * @see #mapRight(Function)
	 */
	<L2> Either<L2, R> mapLeft(Function<L, L2> mapper);

	/**
	 * �E�̒l��ϊ����܂��B
	 *
	 * @param <R2>   �V�����E�̒l
	 * @param mapper �ϊ�����֐�
	 * @return �ϊ���̃I�u�W�F�N�g
	 * @see #map(Function, Function)
	 * @see #mapLeft(Function)
	 */
	<R2> Either<L, R2> mapRight(Function<R, R2> mapper);

	/**
	 * ���̒l��ێ����Ă��āA���l��null�ł͂Ȃ��ꍇ�A�w�肳�ꂽ�֐������s���܂��B
	 *
	 * @param action ���s����֐�
	 * @return ���̃I�u�W�F�N�g���g
	 * @see #ifPresentRight(Consumer)
	 */
	Either<L, R> ifPresentLeft(Consumer<? super L> action);

	/**
	 * �E�̒l��ێ����Ă��āA���l��null�ł͂Ȃ��ꍇ�A�w�肳�ꂽ�֐������s���܂��B
	 *
	 * @param action ���s����֐�
	 * @return ���̃I�u�W�F�N�g���g
	 * @see #ifPresentLeft(Consumer)
	 */
	Either<L, R> ifPresentRight(Consumer<? super R> action);

	/**
	 * �E�̌^��ێ����Ă��邱�Ƃ𔻒肵�܂��B
	 *
	 * @return �E�̌^��ێ����Ă���ꍇ��true
	 * @see #isLeft()
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
	 * @see #orElseThrow(Function)
	 * @see #orElseIllegalArgument()
	 * @see #orElseIllegalState()
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
	 * @see #orElseThrow(Function)
	 * @see #orElseThrowRuntime(Function, Function)
	 * @see #orElseIllegalState()
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
	 * @see #orElseThrow(Function)
	 * @see #orElseThrowRuntime(Function, Function)
	 * @see #orElseIllegalArgument()
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
	 * @see #map(Function, Function)
	 * @see #format(String)
	 */
	default Optional<String> mapToString() {
		return optional().map(Object::toString);
	}

	/**
	 * �ێ����Ă���l�𕶎���ɕҏW���܂��B
	 *
	 * @param format �t�H�[�}�b�g ({@link java.util.Formatter}�Ɠ���)
	 * @return ������
	 * @see #map(Function, Function)
	 * @see #mapToString()
	 */
	default Optional<String> format(String format) {
		return optional().map(value -> String.format(format, value));
	}
}
