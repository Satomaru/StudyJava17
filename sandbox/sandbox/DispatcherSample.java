package sandbox;

import jp.satomaru.util.Dispatcher;
import jp.satomaru.util.function.RetArg1;
import jp.satomaru.util.function.RetArg3;

public class DispatcherSample {

	public record Arguments(int value1, int value2) {
	}

	private static final RetArg3<DispatcherSample, Arguments, String, Integer> DISPATCHER = Dispatcher
		.begin("a", DispatcherSample::add)
		.append("s", DispatcherSample::subtract)
		.append("m", DispatcherSample::multiply)
		.append("d", DispatcherSample::divide)
		.end();

	public static void main(String[] args) {
		DispatcherSample me = new DispatcherSample();
		Arguments arguments = new Arguments(10, 2);
		RetArg1<String, Integer> injected = DISPATCHER.inject(me).inject(arguments);

		injected.run("a").format("a: %s").ifPresent(System.out::println);
		injected.run("s").format("s: %s").ifPresent(System.out::println);
		injected.run("m").format("m: %s").ifPresent(System.out::println);
		injected.run("d").format("d: %s").ifPresent(System.out::println);
		injected.run("x").format("x: %s").ifPresent(System.out::println);
	}

	public int add(Arguments arguments) {
		return arguments.value1 + arguments.value2;
	}

	public int subtract(Arguments arguments) {
		return arguments.value1 - arguments.value2;
	}

	public int multiply(Arguments arguments) {
		return arguments.value1 * arguments.value2;
	}

	public int divide(Arguments arguments) {
		return arguments.value1 / arguments.value2;
	}
}
