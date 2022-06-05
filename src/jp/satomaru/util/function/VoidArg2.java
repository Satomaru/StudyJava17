package jp.satomaru.util.function;

import java.util.Optional;

/**
 * �����ӂ��߂�l�Ȃ��́A��O���X���[����֐��ł��B
 *
 * @author Satomaru
 * @param <A1> ����1
 * @param <A2> ����2
 */
@FunctionalInterface
public interface VoidArg2<A1, A2> {

	/**
	 * ���s���܂��B
	 *
	 * @param arg1 ����1
	 * @param arg2 ����2
	 * @throws Exception �ُ킪���������ꍇ
	 */
	void execute(A1 arg1, A2 arg2) throws Exception;

	/**
	 * ����1�𒍓����܂��B
	 *
	 * @param arg1 ����1
	 * @return ������̊֐�
	 */
	default VoidArg1<A2> inject(A1 arg1) {
		return arg2 -> execute(arg1, arg2);
	}

	/**
	 * ���s���܂��B
	 *
	 * @param arg1 ����1
	 * @param arg2 ����2
	 * @return ����������O
	 */
	default Optional<Exception> run(A1 arg1, A2 arg2) {
		return inject(arg1).inject(arg2).run();
	}

	/**
	 * ���s��A����1��ԋp����֐����쐬���܂��B
	 *
	 * @return �쐬�����֐�
	 */
	default RetArg2<A1, A2, A1> retA1() {
		return (arg1, arg2) -> {
			execute(arg1, arg2);
			return arg1;
		};
	}

	/**
	 * ���s��A����2��ԋp����֐����쐬���܂��B
	 *
	 * @return �쐬�����֐�
	 */
	default RetArg2<A1, A2, A2> retA2() {
		return (arg1, arg2) -> {
			execute(arg1, arg2);
			return arg2;
		};
	}
}
