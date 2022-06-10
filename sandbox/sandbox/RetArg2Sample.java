package sandbox;

import jp.satomaru.util.function.Execute;

public class RetArg2Sample {

	public static void main(String[] args) {
		String result1 = Execute.of(RetArg2Sample::divide)
			.run(10, 2)
			.map(Exception::getMessage, Object::toString)
			.get();

		String result2 = Execute.of(RetArg2Sample::divide)
			.run(10, 0)
			.map(Exception::getMessage, Object::toString)
			.get();

		System.out.println(String.format("divide(10, 2) -> %s", result1));
		System.out.println(String.format("divide(10, 0) -> %s", result2));
	}

	public static int divide(int arg1, int arg2) {
		return arg1 / arg2;
	}
}
