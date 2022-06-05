package jp.satomaru.util.function;

import jp.satomaru.util.Either;

/**
 * �����ЂƂ߂�l����́A��O���X���[����֐��ł��B
 *
 * @author Satomaru
 * @param <A1> ����1
 * @param <R>  �߂�l
 */
@FunctionalInterface
public interface RetArg1<A1, R> {

	/**
	 * ���s���܂��B
	 *
	 * @param arg1 ����1
	 * @return ���s����
	 * @throws Exception �ُ킪���������ꍇ
	 */
	R execute(A1 arg1) throws Exception;

	/**
	 * ����1�𒍓����܂��B
	 *
	 * @param arg1 ����1
	 * @return ������̊֐�
	 */
	default RetArg0<R> inject(A1 arg1) {
		return () -> execute(arg1);
	}

	/**
	 * ���s���܂��B
	 *
	 * @param arg1 ����1
	 * @return ���s����
	 */
	default Either<Exception, R> run(A1 arg1) {
		return inject(arg1).run();
	}
}
