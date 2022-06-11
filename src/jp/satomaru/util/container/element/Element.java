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
	IdentifiableValue<String, V>permits BooleanElement, DoubleElement, InstantElement, IntegerElement, LocalDateTimeElement, LongElement, StringElement {

	/**
	 * エレメントを生成します。
	 *
	 * @param id    識別子
	 * @param value 値
	 * @return エレメント
	 */
	public static BooleanElement of(String id, Boolean value) {
		return new BooleanElement(id, value);
	}

	/**
	 * エレメントを生成します。
	 *
	 * @param id    識別子
	 * @param value 値
	 * @return エレメント
	 */
	public static DoubleElement of(String id, Double value) {
		return new DoubleElement(id, value);
	}

	/**
	 * エレメントを生成します。
	 *
	 * @param id    識別子
	 * @param value 値
	 * @return エレメント
	 */
	public static InstantElement of(String id, Instant value) {
		return new InstantElement(id, value);
	}

	/**
	 * エレメントを生成します。
	 *
	 * @param id    識別子
	 * @param value 値
	 * @return エレメント
	 */
	public static IntegerElement of(String id, Integer value) {
		return new IntegerElement(id, value);
	}

	/**
	 * エレメントを生成します。
	 *
	 * @param id    識別子
	 * @param value 値
	 * @return エレメント
	 */
	public static LocalDateTimeElement of(String id, LocalDateTime value) {
		return new LocalDateTimeElement(id, value);
	}

	/**
	 * エレメントを生成します。
	 *
	 * @param id    識別子
	 * @param value 値
	 * @return エレメント
	 */
	public static LongElement of(String id, Long value) {
		return new LongElement(id, value);
	}

	/**
	 * エレメントを生成します。
	 *
	 * @param id    識別子
	 * @param value 値
	 * @return エレメント
	 */
	public static StringElement of(String id, String value) {
		return new StringElement(id, value);
	}

	@Override
	String id();

	@Override
	V value();

	/**
	 * エレメントパーサーで値を変換し、同じ識別子で新しいエレメントを生成します。
	 *
	 * @param <E>    新しいエレメント
	 * @param parser エレメントパーサー
	 * @return 新しいエレメント
	 * @throws ElementException 値の変換に失敗した場合
	 */
	<E extends Element<?>> E accept(ElementParser<?, E> parser) throws ElementException;

	/**
	 * 値を取得します。
	 *
	 * @return 値
	 */
	default Optional<V> optional() {
		return Optional.ofNullable(value());
	}

	/**
	 * 値が存在しないことを判定します。
	 *
	 * @return 値が存在しない場合はtrue
	 */
	default boolean isEmpty() {
		return optional().isEmpty();
	}

	/**
	 * 値を取得しますが、存在しない場合は例外をスローします。
	 *
	 * @return 値
	 * @throws ElementException 値が存在しない場合
	 */
	default V orElseThrow() throws ElementException {
		return optional().orElseThrow(() -> new ElementException(this, Type.EMPTY));
	}
}
