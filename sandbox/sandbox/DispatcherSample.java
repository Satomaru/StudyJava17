package sandbox;

import java.util.Map;

import jp.satomaru.util.Dispatcher;
import jp.satomaru.util.container.element.ElementException;
import jp.satomaru.util.container.element.ElementSet;
import jp.satomaru.util.container.element.parser.ElementParser;
import jp.satomaru.util.function.RetArg3;

public class DispatcherSample {

	private static final RetArg3<DispatcherSample, ElementSet, String, Integer> DISPATCHER = Dispatcher
		.begin("a", DispatcherSample::add)
		.append("s", DispatcherSample::subtract)
		.append("m", DispatcherSample::multiply)
		.append("d", DispatcherSample::divide)
		.end();

	public static void main(String[] args) {
		if (args.length != 3) {
			throw new IllegalArgumentException("Three arguments are required.");
		}

		ElementSet elements = ElementSet.of(Map.of("arg1", args[1], "arg2", args[2]));
		DispatcherSample me = new DispatcherSample();

		DISPATCHER.run(me, elements, args[0])
			.ifPresentRight(right -> System.out.println(String.format("Result: %s", right)))
			.ifPresentLeft(left -> System.out.println(String.format("Error: %s", left)));
	}

	public int add(ElementSet elements) throws ElementException {
		var parser = elements.parseOrThrowBy(ElementParser.INTEGER);
		return parser.apply("arg1") + parser.apply("arg2");
	}

	public int subtract(ElementSet elements) throws ElementException {
		var parser = elements.parseOrThrowBy(ElementParser.INTEGER);
		return parser.apply("arg1") - parser.apply("arg2");
	}

	public int multiply(ElementSet elements) throws ElementException {
		var parser = elements.parseOrThrowBy(ElementParser.INTEGER);
		return parser.apply("arg1") * parser.apply("arg2");
	}

	public int divide(ElementSet elements) throws ElementException {
		var parser = elements.parseOrThrowBy(ElementParser.INTEGER);
		return parser.apply("arg1") / parser.apply("arg2");
	}
}
