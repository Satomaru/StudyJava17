package jp.satomaru.util.function.core;

import jp.satomaru.util.either.Either;

/**
 * �����Ȃ��̊֐��ɁAEither���T�|�[�g�����܂��B
 *
 * @author Satomaru
 * @param <R> �߂�l
 */
public interface Arg0<R> {

	/**
	 * �֐������s���A���ʂ܂��͗�O���擾���܂��B
	 *
	 * @return ���ʂ܂��͗�O
	 */
	Either<Exception, R> run();
}
