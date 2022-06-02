package jp.satomaru.util.function;

import jp.satomaru.util.either.Either;
import jp.satomaru.util.either.EitherLeft;
import jp.satomaru.util.either.EitherRight;
import jp.satomaru.util.function.core.Arg0;

/**
 * �����Ȃ��߂�l�Ȃ��́A��O���X���[����֐��ł��B
 *
 * @author Satomaru
 */
@FunctionalInterface
public interface VoidArg0 extends Arg0<Void> {

	/**
	 * ���s���܂��B
	 *
	 * @throws Exception �ُ킪���������ꍇ
	 */
	void execute() throws Exception;

	@Override
	default Either<Exception, Void> run() {
		try {
			execute();
			return new EitherRight<>(null);
		} catch (Exception e) {
			return new EitherLeft<>(e);
		}
	}
}
