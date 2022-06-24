package jp.satomaru.util.component;

import jp.satomaru.util.LocalizedException;

public class ComponentException extends LocalizedException {

	private static final long serialVersionUID = 1L;

	public enum ErrorCode {
		EMPTY("{0} is required"),
		UNKNOWN_COMMAND("unknown {0}: {1}"),
		PARSE_BOOLEAN_FAILURE("could not parse {0} as boolean: {1}"),
		PARSE_DOUBLE_FAILURE("could not parse {0} as double: {1}"),
		PARSE_INSTANT_FAILURE("could not parse {0} as instant: {1}"),
		PARSE_INTEGER_FAILURE("could not parse {0} as integer: {1}"),
		PARSE_LOCALDATETIME_FAILURE("could not parse {0} as localDateTime: {1}"),
		PARSE_LONG_FAILURE("could not parse {0} as long: {1}"),
		PARSE_STRING_FAILURE("could not parse {0} as string: {1}"),
		ILLEGAL_VALUE("illegal {0}: {1}"),
		FAILED("{0} failed: {1}");

		private final String message;

		private ErrorCode(String message) {
			this.message = message;
		}
	}

	private final ComponentId id;

	private final ErrorCode errorCode;

	private final Object value;

	public ComponentException(ComponentId id, ErrorCode errorCode, Object value) {
		super();
		this.id = id;
		this.errorCode = errorCode;
		this.value = value;
	}

	public ComponentException(ComponentId id, ErrorCode errorCode, Object value, Throwable cause) {
		super(cause);
		this.id = id;
		this.errorCode = errorCode;
		this.value = value;
	}

	public final ComponentId getId() {
		return id;
	}

	public final ErrorCode getErrorCode() {
		return errorCode;
	}

	public final Object getValue() {
		return value;
	}

	@Override
	protected final String readLocalizedMessage() {
		return Component.getMessage(id, getSubkey(), errorCode.message, value);
	}

	protected String getSubkey() {
		return errorCode.name();
	}
}
