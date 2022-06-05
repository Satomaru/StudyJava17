package jp.satomaru.util.function;

import java.util.Optional;

/**
 * �����ЂƂ߂�l�Ȃ��́A��O���X���[����֐��ł��B
 *
 * @author Satomaru
 * @param <A1> ����1
 */
@FunctionalInterface
public interface VoidArg1<A1> {

	/**
	 * ���s���܂��B
	 *
	 * @param arg1 ����1
	 * @throws Exception �ُ킪���������ꍇ
	 */
	void execute(A1 arg1) throws Exception;

	/**
	 * ����1�𒍓����܂��B
	 *
	 * @param arg1 ����1
	 * @return ������̊֐�
	 */
	default VoidArg0 inject(A1 arg1) {
		return () -> execute(arg1);
	}

	/**
	 * ���s���܂��B
	 *
	 * @param arg1 ����1
	 * @return ����������O
	 */
	default Optional<Exception> run(A1 arg1) {
		return inject(arg1).run();
	}

	/**
	 * ���s��A����1��ԋp����֐����쐬���܂��B
	 *
	 * @return �쐬�����֐�
	 */
	default RetArg1<A1, A1> retA1() {
		return arg1 -> {
			execute(arg1);
			return arg1;
		};
	}
}
