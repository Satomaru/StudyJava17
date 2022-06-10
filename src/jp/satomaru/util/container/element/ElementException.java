package jp.satomaru.util.container.element;

public class ElementException extends Exception {

	private static final long serialVersionUID = 1L;

	public enum Type {
		EMPTY("%1$s is empty"),
		PARSE_BOOLEAN_FAILURE("Could not parse %1$s as boolean: %2$s"),
		PARSE_DOUBLE_FAILURE("Could not parse %1$s as double: %2$s"),
		PARSE_INSTANT_FAILURE("Could not parse %1$s as instant: %2$s"),
		PARSE_INTEGER_FAILURE("Could not parse %1$s as integer: %2$s"),
		PARSE_LOCALDATETIME_FAILURE("Could not parse %1$s as localDateTime: %2$s"),
		PARSE_LONG_FAILURE("Could not parse %1$s as long: %2$s"),
		PARSE_STRING_FAILURE("Could not parse %1$s as string: %2$s");

		private final String message;

		private Type(final String message) {
			this.message = message;
		}

		public String getMessage(Element<?> element) {
			return String.format(message, element.id(), element.value());
		}
	}

	private final Element<?> element;
	private final Type type;

	public ElementException(Element<?> element, Type type) {
		super(type.getMessage(element));
		this.element = element;
		this.type = type;
	}

	public ElementException(Element<?> element, Type type, Throwable cause) {
		super(type.getMessage(element), cause);
		this.element = element;
		this.type = type;
	}

	public Element<?> getElement() {
		return element;
	}

	public Type getType() {
		return type;
	}
}
