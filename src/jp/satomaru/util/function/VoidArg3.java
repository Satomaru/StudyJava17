package jp.satomaru.util.function;

import java.util.Optional;

/**
 * �����݂��߂�l�Ȃ��́A��O���X���[����֐��ł��B
 *
 * @author Satomaru
 * @param <A1> ����1
 * @param <A2> ����2
 * @param <A3> ����3
 */
@FunctionalInterface
public interface VoidArg3<A1, A2, A3> {

	/**
	 * ���s���܂��B
	 *
	 * @param arg1 ����1
	 * @param arg2 ����2
	 * @param arg3 ����3
	 * @throws Exception �ُ킪���������ꍇ
	 */
	void execute(A1 arg1, A2 arg2, A3 arg3) throws Exception;

	/**
	 * ����1�𒍓����܂��B
	 *
	 * @param arg1 ����1
	 * @return ������̊֐�
	 */
	default VoidArg2<A2, A3> inject(A1 arg1) {
		return (arg2, arg3) -> execute(arg1, arg2, arg3);
	}

	/**
	 * ���s���܂��B
	 *
	 * @param arg1 ����1
	 * @param arg2 ����2
	 * @param arg3 ����3
	 * @return ����������O
	 */
	default Optional<Exception> run(A1 arg1, A2 arg2, A3 arg3) {
		return inject(arg1).inject(arg2).inject(arg3).run();
	}

	/**
	 * ���s��A����1��ԋp����֐����쐬���܂��B
	 *
	 * @return �쐬�����֐�
	 */
	default RetArg3<A1, A2, A3, A1> retA1() {
		return (arg1, arg2, arg3) -> {
			execute(arg1, arg2, arg3);
			return arg1;
		};
	}

	/**
	 * ���s��A����2��ԋp����֐����쐬���܂��B
	 *
	 * @return �쐬�����֐�
	 */
	default RetArg3<A1, A2, A3, A2> retA2() {
		return (arg1, arg2, arg3) -> {
			execute(arg1, arg2, arg3);
			return arg2;
		};
	}

	/**
	 * ���s��A����3��ԋp����֐����쐬���܂��B
	 *
	 * @return �쐬�����֐�
	 */
	default RetArg3<A1, A2, A3, A3> retA3() {
		return (arg1, arg2, arg3) -> {
			execute(arg1, arg2, arg3);
			return arg3;
		};
	}
}
