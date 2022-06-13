package jp.satomaru.util.container.element;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.satomaru.util.container.element.parser.ElementParser;
import jp.satomaru.util.function.ThrowableFunction;

/**
 * {@link Element} の集合です。
 *
 * @author Satomaru
 */
public final class ElementSet {

	/**
	 * エレメントセットを生成します。
	 *
	 * @param map 値のマップ
	 * @return エレメントセット (識別子はマップキー)
	 */
	public static ElementSet of(Map<String, ?> map) {
		return new ElementSet(map);

	}

	/**
	 * エレメントセットを生成します。
	 *
	 * @param list 値のリスト
	 * @return エレメントセット (識別子はリストインデックス)
	 */
	public static ElementSet of(List<?> list) {
		HashMap<String, Object> values = new HashMap<>();
		int index = 0;

		for (Object value : list) {
			values.put(Integer.toString(index++), value);
		}

		return new ElementSet(values);
	}

	/**
	 * エレメントセットを生成します。
	 *
	 * @param array 値の配列
	 * @return エレメントセット (識別子は配列インデックス)
	 */
	public static ElementSet array(Object... array) {
		return of(Arrays.asList(array));
	}

	/** エレメント。 */
	private final Map<String, Element<?>> elements;

	private ElementSet(Map<String, ?> values) {
		HashMap<String, Element<?>> elements = new HashMap<>();

		for (Map.Entry<String, ?> entry : values.entrySet()) {
			Element<?> element = Element.of(entry);
			elements.put(element.id(), element);
		}

		this.elements = Map.copyOf(elements);
	}

	/**
	 * エレメントを取得します。
	 *
	 * @return エレメント
	 */
	public Map<String, Element<?>> getElements() {
		return elements;
	}

	/**
	 * エレメントを取得します。
	 *
	 * @param id 識別子
	 * @return エレメント
	 */
	public Element<?> element(String id) {
		return elements.get(id);
	}

	/**
	 * エレメントパーサーを使用して値を取得します。
	 *
	 * @param <V>    値
	 * @param id     識別子
	 * @param parser エレメントパーサー
	 * @return 値
	 * @throws ElementException 値の変換に失敗した場合
	 */
	public <V> V parse(String id, ElementParser<V> parser) throws ElementException {
		return element(id).parse(parser);
	}

	/**
	 * エレメントパーサーを指定して、識別子から値が取得できる関数を作成します。
	 *
	 * @param <V>    値
	 * @param parser エレメントパーサー
	 * @return 作成した関数
	 */
	public <V> ThrowableFunction<String, V, ElementException> parseBy(ElementParser<V> parser) {
		return id -> parse(id, parser);
	}
}
