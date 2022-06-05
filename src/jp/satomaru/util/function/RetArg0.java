package jp.satomaru.util.function;

import jp.satomaru.util.Either;

/**
 * �����Ȃ��߂�l����́A��O���X���[����֐��ł��B
 *
 * @author Satomaru
 * @param <R> �߂�l
 */
@FunctionalInterface
public interface RetArg0<R> {

	/**
	 * ���s���܂��B
	 *
	 * @return ���s����
	 * @throws Exception �ُ킪���������ꍇ
	 */
	R execute() throws Exception;

	/**
	 * ���s���܂��B
	 *
	 * @return ���s����
	 */
	default Either<Exception, R> run() {
		try {
			return new Either.Right<>(execute());
		} catch (Exception e) {
			return new Either.Left<>(e);
		}
	}
}
