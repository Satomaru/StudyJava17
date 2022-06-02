package jp.satomaru.util.function;

import jp.satomaru.util.function.core.Arg1;

/**
 * �����ЂƂ߂�l����́A��O���X���[����֐��ł��B
 *
 * @author Satomaru
 * @param <A1> ����1
 * @param <R>  �߂�l
 */
@FunctionalInterface
public interface RetArg1<A1, R> extends Arg1<A1, R, RetArg0<R>> {

	/**
	 * ���s���܂��B
	 *
	 * @param arg1 ����1
	 * @return ���s����
	 * @throws Exception �ُ킪���������ꍇ
	 */
	R execute(A1 arg1) throws Exception;

	@Override
	default RetArg0<R> inject(A1 arg1) {
		return () -> execute(arg1);
	}
}
