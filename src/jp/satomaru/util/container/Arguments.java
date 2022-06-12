package jp.satomaru.util.container;

import java.util.ArrayList;
import java.util.List;

import jp.satomaru.util.container.element.Element;
import jp.satomaru.util.container.element.ElementException;
import jp.satomaru.util.container.element.parser.ElementParser;
import jp.satomaru.util.function.ThrowableFunction;

/**
 * 配列を {@link Element} として扱います。
 *
 * 各エレメントのIDは "Arguments:%d" です。
 *
 * @author Satomaru
 */
public final class Arguments {

	/**
	 * 配列からアーギュメンツを生成します。
	 *
	 * @param values 配列
	 * @return アーギュメンツ
	 */
	public static Arguments of(Object... values) {
		return new Arguments(values);
	}

	/** エレメント。 */
	private final List<Element<?>> elements;

	private Arguments(Object... values) {
		ArrayList<Element<?>> elements = new ArrayList<>();

		for (int index = 0; index < values.length; index++) {
			String id = String.format("Arguments:%d", index);
			elements.add(Element.of(id, values[index]));
		}

		this.elements = List.copyOf(elements);
	}

	/**
	 * エレメントを取得します。
	 *
	 * @return エレメント
	 */
	public List<Element<?>> getElements() {
		return elements;
	}

	/**
	 * エレメントを取得します。
	 *
	 * @param index インデックス
	 * @return エレメント
	 */
	public Element<?> element(int index) {
		return elements.get(index);
	}

	/**
	 * エレメントパーサーを使用して値を取得します。
	 *
	 * @param <V>    値
	 * @param parser エレメントパーサー
	 * @param index  インデックス
	 * @return 値
	 * @throws ElementException 値の変換に失敗した場合
	 */
	public <V> V parse(ElementParser<V, ?> parser, int index) throws ElementException {
		return element(index).parse(parser);
	}

	/**
	 * エレメントパーサーを指定して、インデックスを与えれば値が取得できる関数を作成します。
	 *
	 * @param <V>    値
	 * @param parser エレメントパーサー
	 * @return 作成した関数
	 */
	public <V> ThrowableFunction<Integer, V, ElementException> parseBy(ElementParser<V, ?> parser) {
		return index -> parse(parser, index);
	}
}
