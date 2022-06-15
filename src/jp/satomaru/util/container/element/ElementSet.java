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

	/** エレメントセットのビルダー。 */
	public static final class Builder {

		private final HashMap<Object, Object> values = new HashMap<>();

		private Builder(Object id, Object value) {
			values.put(id, value);
		}

		/**
		 * エレメントを登録します。
		 *
		 * @param id    エレメントの識別子
		 * @param value エレメントの値
		 * @return エレメントセットのビルダー
		 */
		public Builder put(Object id, Object value) {
			values.put(id, value);
			return this;
		}

		/**
		 * エレメントセットを生成します。
		 *
		 * @return エレメントセット
		 */
		public ElementSet build() {
			return new ElementSet(values);
		}
	}

	/**
	 * エレメントを登録します。
	 *
	 * @param id    エレメントの識別子
	 * @param value エレメントの値
	 * @return エレメントセットのビルダー
	 */
	public static Builder put(Object id, Object value) {
		return new Builder(id, value);
	}

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
	 * @param list 値のリスト
	 * @return エレメントセット (識別子はリストインデックス)
	 */
	public static ElementSet of(List<?> list) {
		HashMap<Object, Object> values = new HashMap<>();
		int index = 0;

		for (Object value : list) {
			values.put(index++, value);
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
