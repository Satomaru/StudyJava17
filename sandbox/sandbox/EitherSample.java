package sandbox;

import jp.satomaru.util.Either;

public class EitherSample {

	public static void main(String[] args) {
		Either<Exception, Integer> either1 = new Either.Right<>(10);
		Either<Exception, Integer> either2 = new Either.Left<>(new IllegalStateException("fail"));

		// STDOUT> Right[value=10]
		System.out.println(either1);

		// STDOUT> Left[value=java.lang.IllegalStateException: fail]
		System.out.println(either2);
	}
}
