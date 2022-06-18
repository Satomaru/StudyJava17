package jp.satomaru.util.component.element;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jp.satomaru.util.component.element.parser.ElementParser;
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
	public static ElementSet of(Map<?, ?> map) {
		return new ElementSet(map);
	}

	/**
	 * エレメントセットを生成します。
	 *
	 * @param iterable 値
	 * @param ids      識別子
	 * @return エレメントセット
	 */
	public static ElementSet of(Iterable<?> iterable, Object... ids) {
		HashMap<Object, Object> values = new HashMap<>();
		Iterator<?> iterator = iterable.iterator();

		for (Object id : ids) {
			Object value = iterator.hasNext() ? iterator.next() : null;
			values.put(id, value);
		}

		return new ElementSet(values);
	}

	/**
	 * エレメントセットを生成します。
	 *
	 * @param array 値
	 * @param ids   識別子
	 * @return エレメントセット
	 */
	public static ElementSet of(Object[] array, Object... ids) {
		return of(Arrays.asList(array), ids);
	}

	/** エレメント。 */
	private final Map<Object, Element<?>> elements;

	private ElementSet(Map<?, ?> values) {
		HashMap<Object, Element<?>> elements = new HashMap<>();

		for (Map.Entry<?, ?> entry : values.entrySet()) {
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
	public Map<Object, Element<?>> elements() {
		return elements;
	}

	/**
	 * エレメントを取得します。
	 *
	 * @param id 識別子
	 * @return エレメント
	 */
	public Element<?> get(Object id) {
		return elements.containsKey(id) ? elements.get(id) : new EmptyElement(id);
	}

	/**
	 * エレメントパーサーを指定して、識別子で値を取得する関数を作成します。
	 *
	 * @param <V>    値
	 * @param parser エレメントパーサー
	 * @return 作成した関数
	 */
	public <V> ThrowableFunction<Object, V, ElementException> parseBy(ElementParser<V> parser) {
		return id -> get(id).parse(parser);
	}

	/**
	 * エレメントパーサーを指定して、識別子で値を取得する関数を作成します。
	 *
	 * @param <V>    値
	 * @param parser エレメントパーサー
	 * @return 作成した関数
	 */
	public <V> ThrowableFunction<Object, V, ElementException> parseOrThrowBy(ElementParser<V> parser) {
		return id -> get(id).parseOrThrow(parser);
	}
}
