package sandbox;

import jp.satomaru.util.component.ComponentException;
import jp.satomaru.util.component.Dispatcher;
import jp.satomaru.util.component.ProcedureException;
import jp.satomaru.util.component.element.ElementSet;
import jp.satomaru.util.component.element.parser.ElementParser;
import jp.satomaru.util.console.StdIn;
import jp.satomaru.util.console.StdOut;

public class DispatcherSample {

	private static final Dispatcher<DispatcherSample, Integer> DISPATCHER = Dispatcher.Builder
		.begin("+", DispatcherSample::add)
		.append("-", DispatcherSample::subtract)
		.append("*", DispatcherSample::multiply)
		.append("/", DispatcherSample::divide)
		.end("operator");

	public static void main(String[] args) {
		var me = new DispatcherSample();

		do {
			var input = StdIn.readSsv("exp> ");

			if (input.isEmpty()) {
				break;
			}

			var elements = ElementSet.of(input, "arg1", "operator", "arg2");

			DISPATCHER.run(me, elements)
				.ifPresentRight(DispatcherSample::whenOk)
				.ifPresentLeft(DispatcherSample::whenNg);
		} while (true);
	}

	private static void whenOk(Integer result) {
		StdOut.writeLine(" = ", result);
	}

	private static void whenNg(ComponentException exception) {
		StdOut.writeLine(" *ERROR* ", exception.getLocalizedMessage());
	}

	public Integer add(ElementSet elements) throws ComponentException {
		var parser = elements.parseOrThrowBy(ElementParser.INTEGER);
		Integer arg1 = parser.apply("arg1");
		Integer arg2 = parser.apply("arg2");
		return arg1 + arg2;
	}

	public Integer subtract(ElementSet elements) throws ComponentException {
		var parser = elements.parseOrThrowBy(ElementParser.INTEGER);
		Integer arg1 = parser.apply("arg1");
		Integer arg2 = parser.apply("arg2");
		return arg1 - arg2;
	}

	public Integer multiply(ElementSet elements) throws ComponentException {
		var parser = elements.parseOrThrowBy(ElementParser.INTEGER);
		Integer arg1 = parser.apply("arg1");
		Integer arg2 = parser.apply("arg2");
		return arg1 * arg2;
	}

	public Integer divide(ElementSet elements) throws ComponentException {
		var parser = elements.parseOrThrowBy(ElementParser.INTEGER);
		Integer arg1 = parser.apply("arg1");
		Integer arg2 = parser.apply("arg2");

		if (arg2 == 0) {
			throw new ProcedureException("divide", "DIVIDE_BY_ZERO");
		}

		return arg1 / arg2;
	}
}
