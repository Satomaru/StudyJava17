package jp.satomaru.util.function.core;

import jp.satomaru.util.either.Either;

/**
 * �����ЂƂ̊֐��ɁAEither���T�|�[�g�����܂��B
 *
 * @author Satomaru
 * @param <A1> ����1
 * @param <R>  �߂�l
 * @param <I>  ����1�𒍓�������̊֐�
 */
public interface Arg1<A1, R, I extends Arg0<R>> {

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
	 * @return ���ʂ܂��͗�O
	 */
	default Either<Exception, R> run(A1 arg1) {
		return inject(arg1).run();
	}
}
