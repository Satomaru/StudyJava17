package jp.satomaru.util.component.element;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import jp.satomaru.util.IdentifiableValue;
import jp.satomaru.util.component.element.ElementException.ErrorCode;
import jp.satomaru.util.component.element.parser.ElementParser;

/**
 * 識別子と値を保持する不変オブジェクトです。
 *
 * @author Satomaru
 *
 * @param <V> 値
 */
public sealed interface Element<V> extends
	IdentifiableValue<Object, V>permits BooleanElement, DoubleElement, EmptyElement, InstantElement, IntegerElement, LocalDateTimeElement, LongElement, StringElement {

	/**
	 * エレメントを生成します。
	 *
	 * @param id    識別子
	 * @param value 値
	 * @return エレメント
	 * @throws IllegalArgumentException 値の型がサポートされていない場合
	 */
	public static Element<?> of(Object id, Object value) {
		if (value == null) {
			return new EmptyElement(id);
		}

		if (value instanceof Boolean booleanValue) {
			return new BooleanElement(id, booleanValue);
		}

		if (value instanceof Double doubleValue) {
			return new DoubleElement(id, doubleValue);
		}

		if (value instanceof Instant instant) {
			return new InstantElement(id, instant);
		}

		if (value instanceof Integer integer) {
			return new IntegerElement(id, integer);
		}

		if (value instanceof LocalDateTime localDateTime) {
			return new LocalDateTimeElement(id, localDateTime);
		}

		if (value instanceof Long longValue) {
			return new LongElement(id, longValue);
		}

		if (value instanceof String string) {
			return new StringElement(id, string);
		}

		String type = value.getClass().getSimpleName();
		throw new IllegalArgumentException(String.format("unsupported type: %s", type));
	}

	/**
	 * エレメントを生成します。
	 *
	 * @param value 識別子付き値
	 * @return エレメント
	 * @throws IllegalArgumentException 値の型がサポートされていない場合
	 */
	public static Element<?> of(IdentifiableValue<?, ?> value) {
		return of(value.id(), value.value());
	}

	/**
	 * エレメントを生成します。
	 *
	 * @param entry 識別子をキーとしたマップのエントリ
	 * @return エレメント
	 */
	public static Element<?> of(Map.Entry<?, ?> entry) {
		return of(entry.getKey(), entry.getValue());
	}

	@Override
	Object id();

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
	 * @param parser エレメントパーサー
	 * @return 新しいエレメント
	 * @throws ElementException 値の変換に失敗した場合
	 */
	Element<?> map(ElementParser<?> parser) throws ElementException;

	/**
	 * エレメントパーサーで値を変換して返却します。
	 *
	 * @param <P>    変換後の値
	 * @param parser エレメントパーサー
	 * @return 変換後の値
	 * @throws ElementException 値の変換に失敗した場合
	 */
	<P> P parse(ElementParser<P> parser) throws ElementException;

	/**
	 * エレメントパーサーで値を変換して返却しますが、値が存在しない場合は例外をスローします。
	 *
	 * @param <P>    変換後の値
	 * @param parser エレメントパーサー
	 * @return 変換後の値
	 * @throws ElementException 値が存在しない、または値の変換に失敗した場合
	 */
	default <P> P parseOrThrow(ElementParser<P> parser) throws ElementException {
		return Optional.ofNullable(parse(parser)).orElseThrow(() -> new ElementException(this, ErrorCode.EMPTY));
	}
}
