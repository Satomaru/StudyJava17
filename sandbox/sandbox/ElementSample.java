package sandbox;

import jp.satomaru.util.container.element.Element;
import jp.satomaru.util.container.element.parser.ElementParser;

/**
 * Elementのサンプル。
 *
 * @author Satomaru
 */
public class ElementSample {

	public static void main(String[] args) throws Exception {
		System.out.println(test("foo", "2022/06/11 02:45:30.125"));
		System.out.println("----");
		System.out.println(test("bar", null));
	}

	private static String test(String id, String string) throws Exception {
		var element1 = Element.of(id, string);
		var element2 = element1.map(ElementParser.LOCALDATETIME);
		var element3 = element2.map(ElementParser.LONG);
		var element4 = element3.map(ElementParser.INSTANT);
		var element5 = element4.map(ElementParser.LOCALDATETIME);
		var result = element5.parse(ElementParser.STRING);

		System.out.println(element1.description());
		System.out.println(element2.description());
		System.out.println(element3.description());
		System.out.println(element4.description());
		System.out.println(element5.description());
		return result;
	}
}
