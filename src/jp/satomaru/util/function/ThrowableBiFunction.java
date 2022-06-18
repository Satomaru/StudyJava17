package jp.satomaru.util.function;

import jp.satomaru.util.Either;

@FunctionalInterface
public interface ThrowableBiFunction<T, U, R, E extends Exception> {

	R apply(T arg1, U arg2) throws E;

	default Either<E, R> run(T arg1, U arg2) {
		try {
			return new Either.Right<>(apply(arg1, arg2));
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			@SuppressWarnings("unchecked")
			E left = (E) e;

			return new Either.Left<>(left);
		}
	}
}
