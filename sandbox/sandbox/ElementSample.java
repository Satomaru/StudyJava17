package sandbox;

import jp.satomaru.util.container.element.Element;
import jp.satomaru.util.container.element.ElementException;
import jp.satomaru.util.container.element.parser.ElementParser;

/**
 * Elementのサンプル。
 *
 * <pre>
 *   {&lt;String&gt; element: 2022/06/11 02:45:30.125}
 *   {&lt;LocalDateTime&gt; element: 2022-06-11T02:45:30.125}
 *   {&lt;Long&gt; element: 1654883130125}
 *   {&lt;Instant&gt; element: 2022-06-10T17:45:30.125Z}
 *   {&lt;LocalDateTime&gt; element: 2022-06-11T02:45:30.125}
 *   {&lt;String&gt; element: 2022/06/11 02:45:30.125}
 * </pre>
 *
 * @author Satomaru
 */
public class ElementSample {

	public static void main(String[] args) throws ElementException {
		var element1 = Element.of("element", "2022/06/11 02:45:30.125");
		var element2 = element1.accept(ElementParser.LOCALDATETIME);
		var element3 = element2.accept(ElementParser.LONG);
		var element4 = element3.accept(ElementParser.INSTANT);
		var element5 = element4.accept(ElementParser.LOCALDATETIME);
		var element6 = element5.accept(ElementParser.STRING);

		System.out.println(element1.description());
		System.out.println(element2.description());
		System.out.println(element3.description());
		System.out.println(element4.description());
		System.out.println(element5.description());
		System.out.println(element6.description());
	}
}
