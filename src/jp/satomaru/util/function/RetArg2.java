package jp.satomaru.util.function;

import jp.satomaru.util.function.core.Arg2;

/**
 * �����ӂ��߂�l����́A��O���X���[����֐��ł��B
 *
 * @author Satomaru
 * @param <A1> ����1
 * @param <A2> ����2
 * @param <R>  �߂�l
 */
@FunctionalInterface
public interface RetArg2<A1, A2, R> extends Arg2<A1, A2, R, RetArg1<A2, R>> {

	/**
	 * ���s���܂��B
	 *
	 * @param arg1 ����1
	 * @param arg2 ����2
	 * @return ���s����
	 * @throws Exception �ُ킪���������ꍇ
	 */
	R execute(A1 arg1, A2 arg2) throws Exception;

	@Override
	default RetArg1<A2, R> inject(A1 arg1) {
		return arg2 -> execute(arg1, arg2);
	}
}
