package jp.satomaru.util.function;

import jp.satomaru.util.function.core.Arg3;

/**
 * �����݂��߂�l����́A��O���X���[����֐��ł��B
 *
 * @author Satomaru
 * @param <A1> ����1
 * @param <A2> ����2
 * @param <A3> ����3
 * @param <R>  �߂�l
 */
@FunctionalInterface
public interface RetArg3<A1, A2, A3, R> extends Arg3<A1, A2, A3, R, RetArg2<A2, A3, R>> {

	/**
	 * ���s���܂��B
	 *
	 * @param arg1 ����1
	 * @param arg2 ����2
	 * @param arg3 ����3
	 * @return ���s����
	 * @throws Exception �ُ킪���������ꍇ
	 */
	R execute(A1 arg1, A2 arg2, A3 arg3) throws Exception;

	@Override
	default RetArg2<A2, A3, R> inject(A1 arg1) {
		return (arg2, arg3) -> execute(arg1, arg2, arg3);
	}
}
