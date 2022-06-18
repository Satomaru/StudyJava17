package jp.satomaru.util.component.element;

import jp.satomaru.util.component.ComponentException;

/**
 * エレメントの操作に関する例外です。
 *
 * @author Satomaru
 */
public final class ElementException extends ComponentException {

	private static final long serialVersionUID = 1L;

	public enum ErrorCode {
		/** 値が存在しない。 */
		EMPTY("{0} is required"),

		/** ブーリアンへの変換に失敗。 */
		PARSE_BOOLEAN_FAILURE("could not parse {0} as boolean: {1}"),

		/** 倍精度浮動小数点数への変換に失敗。 */
		PARSE_DOUBLE_FAILURE("could not parse {0} as double: {1}"),

		/** インスタントへの変換に失敗。 */
		PARSE_INSTANT_FAILURE("could not parse {0} as instant: {1}"),

		/** 整数への変換に失敗。 */
		PARSE_INTEGER_FAILURE("could not parse {0} as integer: {1}"),

		/** ローカル日時への変換に失敗。 */
		PARSE_LOCALDATETIME_FAILURE("could not parse {0} as localDateTime: {1}"),

		/** 長整数への変換に失敗。 */
		PARSE_LONG_FAILURE("could not parse {0} as long: {1}"),

		/** 文字列への変換に失敗。 */
		PARSE_STRING_FAILURE("could not parse {0} as string: {1}");

		private final String defaultMessage;

		private ErrorCode(final String defaultMessage) {
			this.defaultMessage = defaultMessage;
		}
	}

	private final Element<?> element;

	private final ErrorCode errorCode;

	public ElementException(Element<?> element, ErrorCode errorCode) {
		super();
		this.element = element;
		this.errorCode = errorCode;
	}

	public ElementException(Element<?> element, ErrorCode errorCode, Throwable cause) {
		super(cause);
		this.element = element;
		this.errorCode = errorCode;
	}

	@Override
	public String getComponentId() {
		return element.id().toString();
	}

	@Override
	public String getErrorCode() {
		return errorCode.name();
	}

	public Element<?> getElement() {
		return element;
	}

	@Override
	protected String getDefaultMessage() {
		return errorCode.defaultMessage;
	}

	@Override
	protected Object[] getParameters() {
		return new Object[] {
			element.id(),
			element.value()
		};
	}
}
