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

		injected.run("a").format("a: %s").ifPresent(System.out::println);
		injected.run("s").format("s: %s").ifPresent(System.out::println);
		injected.run("m").format("m: %s").ifPresent(System.out::println);
		injected.run("d").format("d: %s").ifPresent(System.out::println);
		injected.run("?").format("?: %s").ifPresent(System.out::println);
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
