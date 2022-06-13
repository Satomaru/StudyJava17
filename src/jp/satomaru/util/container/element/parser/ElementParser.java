package jp.satomaru.util.container.element.parser;

import jp.satomaru.util.container.element.BooleanElement;
import jp.satomaru.util.container.element.DoubleElement;
import jp.satomaru.util.container.element.Element;
import jp.satomaru.util.container.element.ElementException;
import jp.satomaru.util.container.element.ElementException.Type;
import jp.satomaru.util.container.element.InstantElement;
import jp.satomaru.util.container.element.IntegerElement;
import jp.satomaru.util.container.element.LocalDateTimeElement;
import jp.satomaru.util.container.element.LongElement;
import jp.satomaru.util.container.element.StringElement;
import jp.satomaru.util.function.RetArg1;

/**
 * エレメントの値を変換して、同じ識別子で新しいエレメントを生成します。
 *
 * @author Satomaru
 *
 * @param <V> 新しい値
 */
public sealed abstract class ElementParser<V> permits ToBoolean, ToDouble, ToInstant, ToInteger, ToLocalDateTime, ToLong, ToString {

	/** ブーリアンに変換するエレメントパーサー。 */
	public static final ToBoolean BOOLEAN = new ToBoolean();

	/** 倍精度浮動小数点数に変換するエレメントパーサー。 */
	public static final ToDouble DOUBLE = new ToDouble();

	/** インスタントに変換するエレメントパーサー。 */
	public static final ToInstant INSTANT = new ToInstant();

	/** 整数に変換するエレメントパーサー。 */
	public static final ToInteger INTEGER = new ToInteger();

	/** ローカル日時に変換するエレメントパーサー。 */
	public static final ToLocalDateTime LOCALDATETIME = new ToLocalDateTime();

	/** 長整数に変換するエレメントパーサー。 */
	public static final ToLong LONG = new ToLong();

	/** 文字列に変換するエレメントパーサー。 */
	public static final ToString STRING = new ToString();

	/**
	 * ブーリアンの値を変換します。
	 *
	 * @param element ブーリアンのエレメント
	 * @return 変換後のエレメント
	 * @throws ElementException 変換に失敗した場合
	 */
	public Element<V> parse(BooleanElement element) throws ElementException {
		throw new ElementException(element, typeWhenParseFailure());
	}

	/**
	 * 倍精度浮動小数点数の値を変換します。
	 *
	 * @param element ブーリアンのエレメント
	 * @return 変換後のエレメント
	 * @throws ElementException 変換に失敗した場合
	 */
	public Element<V> parse(DoubleElement element) throws ElementException {
		throw new ElementException(element, typeWhenParseFailure());
	}

	/**
	 * インスタントの値を変換します。
	 *
	 * @param element ブーリアンのエレメント
	 * @return 変換後のエレメント
	 * @throws ElementException 変換に失敗した場合
	 */
	public Element<V> parse(InstantElement element) throws ElementException {
		throw new ElementException(element, typeWhenParseFailure());
	}

	/**
	 * 整数の値を変換します。
	 *
	 * @param element ブーリアンのエレメント
	 * @return 変換後のエレメント
	 * @throws ElementException 変換に失敗した場合
	 */
	public Element<V> parse(IntegerElement element) throws ElementException {
		throw new ElementException(element, typeWhenParseFailure());
	}

	/**
	 * ローカル日時の値を変換します。
	 *
	 * @param element ブーリアンのエレメント
	 * @return 変換後のエレメント
	 * @throws ElementException 変換に失敗した場合
	 */
	public Element<V> parse(LocalDateTimeElement element) throws ElementException {
		throw new ElementException(element, typeWhenParseFailure());
	}

	/**
	 * 長整数の値を変換します。
	 *
	 * @param element ブーリアンのエレメント
	 * @return 変換後のエレメント
	 * @throws ElementException 変換に失敗した場合
	 */
	public Element<V> parse(LongElement element) throws ElementException {
		throw new ElementException(element, typeWhenParseFailure());
	}

	/**
	 * 文字列の値を変換します。
	 *
	 * @param element ブーリアンのエレメント
	 * @return 変換後のエレメント
	 * @throws ElementException 変換に失敗した場合
	 */
	public Element<V> parse(StringElement element) throws ElementException {
		throw new ElementException(element, typeWhenParseFailure());
	}

	protected final <F> Element<V> map(Element<F> element, RetArg1<F, V> parser) throws ElementException {
		try {
			return set(element, parser.execute(element.value()));
		} catch (ElementException e) {
			throw e;
		} catch (Exception e) {
			throw new ElementException(element, typeWhenParseFailure(), e);
		}
	}

	protected abstract Element<V> set(Element<?> element, V newValue);

	protected abstract Type typeWhenParseFailure();
}
