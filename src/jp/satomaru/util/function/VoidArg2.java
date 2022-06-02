package jp.satomaru.util.function;

import jp.satomaru.util.function.core.Arg2;

/**
 * �����ӂ��߂�l�Ȃ��́A��O���X���[����֐��ł��B
 *
 * @author Satomaru
 * @param <A1> ����1
 * @param <A2> ����2
 */
@FunctionalInterface
public interface VoidArg2<A1, A2> extends Arg2<A1, A2, Void, RetArg1<A2, Void>> {

	/**
	 * ���s���܂��B
	 *
	 * @param arg1 ����1
	 * @param arg2 ����2
	 * @throws Exception �ُ킪���������ꍇ
	 */
	void execute(A1 arg1, A2 arg2) throws Exception;

	@Override
	default RetArg1<A2, Void> inject(A1 arg1) {
		return arg2 -> {
			execute(arg1, arg2);
			return null;
		};
	}
}
