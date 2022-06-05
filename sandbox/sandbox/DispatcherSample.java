package sandbox;

import java.util.function.IntBinaryOperator;

import jp.satomaru.util.Dispatcher;
import jp.satomaru.util.function.RetArg1;

public class DispatcherSample {

	public record Param(int int1, int int2) {

		public int calculate(IntBinaryOperator operator) {
			return operator.applyAsInt(int1, int2);
		}
	}

	private static final Dispatcher<DispatcherSample, Param, Integer> DISPATCHER = new Dispatcher.Factory<DispatcherSample, Param, Integer>()
		.add("a", DispatcherSample::add)
		.add("s", DispatcherSample::subtract)
		.add("m", DispatcherSample::multiply)
		.add("d", DispatcherSample::divide)
		.end();

	public static void main(String[] args) {
		DispatcherSample me = new DispatcherSample();
		Param param = new Param(10, 2);
		RetArg1<String, Integer> injected = DISPATCHER.inject(me, param);

		injected.run("a").mapToString().ifPresent(result -> System.out.println("a: " + result));
		injected.run("s").mapToString().ifPresent(result -> System.out.println("s: " + result));
		injected.run("m").mapToString().ifPresent(result -> System.out.println("m: " + result));
		injected.run("d").mapToString().ifPresent(result -> System.out.println("d: " + result));
		injected.run("?").mapToString().ifPresent(result -> System.out.println("?: " + result));
	}

	public int add(Param param) {
		return param.calculate((int1, int2) -> int1 + int2);
	}

	public int subtract(Param param) {
		return param.calculate((int1, int2) -> int1 - int2);
	}

	public int multiply(Param param) {
		return param.calculate((int1, int2) -> int1 * int2);
	}

	public int divide(Param param) {
		return param.calculate((int1, int2) -> int1 / int2);
	}
}
