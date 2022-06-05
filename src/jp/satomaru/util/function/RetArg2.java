package jp.satomaru.util.function;

import jp.satomaru.util.Either;

/**
 * �����ӂ��߂�l����́A��O���X���[����֐��ł��B
 *
 * @author Satomaru
 * @param <A1> ����1
 * @param <A2> ����2
 * @param <R>  �߂�l
 */
@FunctionalInterface
public interface RetArg2<A1, A2, R> {

	/**
	 * ���s���܂��B
	 *
	 * @param arg1 ����1
	 * @param arg2 ����2
	 * @return ���s����
	 * @throws Exception �ُ킪���������ꍇ
	 */
	R execute(A1 arg1, A2 arg2) throws Exception;

	/**
	 * ����1�𒍓����܂��B
	 *
	 * @param arg1 ����1
	 * @return ������̊֐�
	 */
	default RetArg1<A2, R> inject(A1 arg1) {
		return arg2 -> execute(arg1, arg2);
	}

	/**
	 * ���s���܂��B
	 *
	 * @param arg1 ����1
	 * @param arg2 ����2
	 * @return ���s����
	 */
	default Either<Exception, R> run(A1 arg1, A2 arg2) {
		return inject(arg1).inject(arg2).run();
	}
}
