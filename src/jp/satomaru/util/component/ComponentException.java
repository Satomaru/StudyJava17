package jp.satomaru.util.component;

import java.text.MessageFormat;

import jp.satomaru.util.LocalizedException;
import jp.satomaru.util.ResourceAccessor;

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
		FAILED("{0} failed: {1}");

		private final String defaultMessage;

		private ErrorCode(String defaultMessage) {
			this.defaultMessage = defaultMessage;
		}
	}

	private static final ResourceAccessor ACCESSOR = ResourceAccessor.of(
		ComponentException.class.getPackage(), "messages");

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
		String caption = ACCESSOR.get(id.key());

		if (caption == null) {
			caption = id.name();
		}

		String subkey = getMessaggeSubkey();
		String message = ACCESSOR.get(id.key(subkey));

		if (message == null) {
			message = ACCESSOR.get(id.keyWithoutName(subkey));
		}

		if (message == null) {
			message = errorCode.defaultMessage;
		}

		return MessageFormat.format(message, caption, value);
	}

	protected String getMessaggeSubkey() {
		return errorCode.name();
	}
}
