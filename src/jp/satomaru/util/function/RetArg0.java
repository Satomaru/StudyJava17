package jp.satomaru.util.function;

import jp.satomaru.util.either.Either;
import jp.satomaru.util.either.EitherLeft;
import jp.satomaru.util.either.EitherRight;
import jp.satomaru.util.function.core.Arg0;

/**
 * �����Ȃ��߂�l����́A��O���X���[����֐��ł��B
 *
 * @author Satomaru
 * @param <R> �߂�l
 */
@FunctionalInterface
public interface RetArg0<R> extends Arg0<R> {

	/**
	 * ���s���܂��B
	 *
	 * @return ���s����
	 * @throws Exception �ُ킪���������ꍇ
	 */
	R execute() throws Exception;

	@Override
	default Either<Exception, R> run() {
		try {
			return new EitherRight<>(execute());
		} catch (Exception e) {
			return new EitherLeft<>(e);
		}
	}
}
