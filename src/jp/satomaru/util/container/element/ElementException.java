package jp.satomaru.util.container.element;

/**
 * エレメントの操作に関する例外です。
 *
 * @author Satomaru
 */
public class ElementException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 例外の種類。
	 *
	 * @author Satomaru
	 */
	public enum Type {
		/** 値が存在しない。 */
		EMPTY("%1$s is empty"),

		/** ブーリアンへの変換に失敗。 */
		PARSE_BOOLEAN_FAILURE("could not parse %1$s as boolean: %2$s"),

		/** 倍精度浮動小数点数への変換に失敗。 */
		PARSE_DOUBLE_FAILURE("could not parse %1$s as double: %2$s"),

		/** インスタントへの変換に失敗。 */
		PARSE_INSTANT_FAILURE("could not parse %1$s as instant: %2$s"),

		/** 整数への変換に失敗。 */
		PARSE_INTEGER_FAILURE("could not parse %1$s as integer: %2$s"),

		/** ローカル日時への変換に失敗。 */
		PARSE_LOCALDATETIME_FAILURE("could not parse %1$s as localDateTime: %2$s"),

		/** 長整数への変換に失敗。 */
		PARSE_LONG_FAILURE("could not parse %1$s as long: %2$s"),

		/** 文字列への変換に失敗。 */
		PARSE_STRING_FAILURE("could not parse %1$s as string: %2$s");

		private final String message;

		private Type(final String message) {
			this.message = message;
		}

		public String getMessage(Element<?> element) {
			return String.format(message, element.id(), element.value());
		}
	}

	/** 例外が発生したエレメント。 */
	private final Element<?> element;

	/** 例外の種類。 */
	private final Type type;

	/**
	 * エレメントの操作に関する例外を生成します。
	 *
	 * @param element 例外が発生したエレメント
	 * @param type    例外の種類
	 */
	public ElementException(Element<?> element, Type type) {
		super(type.getMessage(element));
		this.element = element;
		this.type = type;
	}

	/**
	 * エレメントの操作に関する例外を生成します。
	 *
	 * @param element 例外が発生したエレメント
	 * @param type    例外の種類
	 * @param cause   起因となる例外
	 */
	public ElementException(Element<?> element, Type type, Throwable cause) {
		super(type.getMessage(element), cause);
		this.element = element;
		this.type = type;
	}

	/**
	 * 例外が発生したエレメントを取得します。
	 *
	 * @return 例外が発生したエレメント
	 */
	public Element<?> getElement() {
		return element;
	}

	/**
	 * 例外の種類を取得します。
	 *
	 * @return 例外の種類
	 */
	public Type getType() {
		return type;
	}
}
