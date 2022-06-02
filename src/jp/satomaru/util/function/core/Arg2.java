package jp.satomaru.util.function.core;

import jp.satomaru.util.either.Either;

/**
 * �����ӂ��̊֐��ɁAEither���T�|�[�g�����܂��B
 *
 * @author Satomaru
 * @param <A1> ����1
 * @param <A2> ����2
 * @param <R>  �߂�l
 * @param <I>  ����1�𒍓�������̊֐�
 */
public interface Arg2<A1, A2, R, I extends Arg1<A2, R, ?>> {

	/**
	 * ����1�𒍓����܂��B
	 *
	 * @param arg1 ����1
	 * @return ������̊֐�
	 */
	I inject(A1 arg1);

	/**
	 * �֐������s���A���ʂ܂��͗�O���擾���܂��B
	 *
	 * @param arg1 ����1
	 * @param arg2 ����2
	 * @return ���ʂ܂��͗�O
	 */
	default Either<Exception, R> run(A1 arg1, A2 arg2) {
		return inject(arg1).inject(arg2).run();
	}
}
