package sandbox;

import java.time.LocalDateTime;

import jp.satomaru.util.container.Arguments;
import jp.satomaru.util.container.element.ElementException;
import jp.satomaru.util.container.element.parser.ElementParser;

public class ArgumentsSample {

	public static void main(String[] args) throws ElementException {
		var arguments = Arguments.of("FOO", 100, true, LocalDateTime.now());
		var parser = arguments.parseBy(ElementParser.STRING);

		System.out.println(parser.apply(0));
		System.out.println(parser.apply(1));
		System.out.println(parser.apply(2));
		System.out.println(parser.apply(3));
	}
}
