package jp.satomaru.util.function;

import jp.satomaru.util.function.core.Arg3;

/**
 * �����݂��߂�l�Ȃ��́A��O���X���[����֐��ł��B
 *
 * @author Satomaru
 * @param <A1> ����1
 * @param <A2> ����2
 * @param <A3> ����3
 */
@FunctionalInterface
public interface VoidArg3<A1, A2, A3> extends Arg3<A1, A2, A3, Void, RetArg2<A2, A3, Void>> {

	/**
	 * ���s���܂��B
	 *
	 * @param arg1 ����1
	 * @param arg2 ����2
	 * @param arg3 ����3
	 * @throws Exception �ُ킪���������ꍇ
	 */
	void execute(A1 arg1, A2 arg2, A3 arg3) throws Exception;

	@Override
	default RetArg2<A2, A3, Void> inject(A1 arg1) {
		return (arg2, arg3) -> {
			execute(arg1, arg2, arg3);
			return null;
		};
	}
}
