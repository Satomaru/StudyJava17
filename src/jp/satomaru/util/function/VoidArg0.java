package jp.satomaru.util.function;

import java.util.Optional;

/**
 * �����Ȃ��߂�l�Ȃ��́A��O���X���[����֐��ł��B
 *
 * @author Satomaru
 */
@FunctionalInterface
public interface VoidArg0 {

	/**
	 * ���s���܂��B
	 *
	 * @throws Exception �ُ킪���������ꍇ
	 */
	void execute() throws Exception;

	/**
	 * ���s���܂��B
	 *
	 * @return ����������O
	 */
	default Optional<Exception> run() {
		try {
			execute();
			return Optional.empty();
		} catch (Exception e) {
			return Optional.of(e);
		}
	}
}
