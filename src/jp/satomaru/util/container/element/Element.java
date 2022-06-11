package jp.satomaru.util.container.element;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import jp.satomaru.util.container.IdentifiableValue;
import jp.satomaru.util.container.element.ElementException.Type;
import jp.satomaru.util.container.element.parser.ElementParser;

/**
 * 識別子と値を保持する不変オブジェクトです。
 *
 * @author Satomaru
 *
 * @param <V> 値
 */
public sealed interface Element<V> extends
	IdentifiableValue<String, V>permits BooleanElement, DoubleElement, EmptyElement, InstantElement, IntegerElement, LocalDateTimeElement, LongElement, StringElement {

	/**
	 * 値のないエレメントを生成します。
	 *
	 * @param id 識別子
	 * @return エレメント
	 */
	public static Element<?> empty(String id) {
		return new EmptyElement(id);
	}

	/**
	 * エレメントを生成します。
	 *
	 * @param id    識別子
	 * @param value 値
	 * @return エレメント
	 */
	public static Element<?> of(String id, Boolean value) {
		return (value == null) ? empty(id) : new BooleanElement(id, value);
	}

	/**
	 * エレメントを生成します。
	 *
	 * @param id    識別子
	 * @param value 値
	 * @return エレメント
	 */
	public static Element<?> of(String id, Double value) {
		return (value == null) ? empty(id) : new DoubleElement(id, value);
	}

	/**
	 * エレメントを生成します。
	 *
	 * @param id    識別子
	 * @param value 値
	 * @return エレメント
	 */
	public static Element<?> of(String id, Instant value) {
		return (value == null) ? empty(id) : new InstantElement(id, value);
	}

	/**
	 * エレメントを生成します。
	 *
	 * @param id    識別子
	 * @param value 値
	 * @return エレメント
	 */
	public static Element<?> of(String id, Integer value) {
		return (value == null) ? empty(id) : new IntegerElement(id, value);
	}

	/**
	 * エレメントを生成します。
	 *
	 * @param id    識別子
	 * @param value 値
	 * @return エレメント
	 */
	public static Element<?> of(String id, LocalDateTime value) {
		return (value == null) ? empty(id) : new LocalDateTimeElement(id, value);
	}

	/**
	 * エレメントを生成します。
	 *
	 * @param id    識別子
	 * @param value 値
	 * @return エレメント
	 */
	public static Element<?> of(String id, Long value) {
		return (value == null) ? empty(id) : new LongElement(id, value);
	}

	/**
	 * エレメントを生成します。
	 *
	 * @param id    識別子
	 * @param value 値
	 * @return エレメント
	 */
	public static Element<?> of(String id, String value) {
		return (value == null) ? empty(id) : new StringElement(id, value);
	}

	@Override
	String id();

	@Override
	V value();

	/**
	 * 値がないことを判定します。
	 *
	 * @return 値がない場合はtrue
	 */
	default boolean isEmpty() {
		return value() == null;
	}

	/**
	 * エレメントパーサーで値を変換し、同じ識別子で新しいエレメントを生成します。
	 *
	 * @param <E>    新しいエレメント
	 * @param parser エレメントパーサー
	 * @return 新しいエレメント
	 * @throws ElementException 値の変換に失敗した場合
	 */
	Element<?> map(ElementParser<?, ?> parser) throws ElementException;

	/**
	 * エレメントパーサーで値を変換して返却します。
	 *
	 * @param <P>    変換後の値
	 * @param <E>    変換後のエレメント
	 * @param parser エレメントパーサー
	 * @return 変換後の値
	 * @throws ElementException 値の変換に失敗した場合
	 */
	<P, E extends Element<P>> P parse(ElementParser<P, E> parser) throws ElementException;

	/**
	 * エレメントパーサーで値を変換して返却しますが、値が存在しない場合は例外をスローします。
	 *
	 * @param <P>    変換後の値
	 * @param <E>    変換後のエレメント
	 * @param parser エレメントパーサー
	 * @return 変換後の値
	 * @throws ElementException 値が存在しない、または値の変換に失敗した場合
	 */
	default <P, E extends Element<P>> P parseOrThrow(ElementParser<P, E> parser) throws ElementException {
		return Optional.ofNullable(parse(parser)).orElseThrow(() -> new ElementException(this, Type.EMPTY));
	}
}
