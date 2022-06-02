package jp.satomaru.util.function;

import jp.satomaru.util.function.core.Arg1;

/**
 * �����ЂƂ߂�l�Ȃ��́A��O���X���[����֐��ł��B
 *
 * @author Satomaru
 * @param <A1> ����1
 */
@FunctionalInterface
public interface VoidArg1<A1> extends Arg1<A1, Void, RetArg0<Void>> {

	/**
	 * ���s���܂��B
	 *
	 * @param arg1 ����1
	 * @throws Exception �ُ킪���������ꍇ
	 */
	void execute(A1 arg1) throws Exception;

	@Override
	default RetArg0<Void> inject(A1 arg1) {
		return () -> {
			execute(arg1);
			return null;
		};
	}
}
