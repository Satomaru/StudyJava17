package jp.satomaru.util.component;

import java.text.MessageFormat;

import jp.satomaru.util.LocalizedException;
import jp.satomaru.util.ResourceAccessor;

public abstract class ComponentException extends LocalizedException {

	private static final long serialVersionUID = 1L;

	private static final ResourceAccessor ACCESSOR = ResourceAccessor.of(
		ComponentException.class.getPackage(), "messages");

	public ComponentException() {
		super();
	}

	public ComponentException(String message) {
		super(message);
	}

	public ComponentException(Throwable cause) {
		super(cause);
	}

	public ComponentException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	protected final String readLocalizedMessage() {
		var key = new ResourceAccessor.Key(getClass(), getComponentId(), getErrorCode());
		var value = ACCESSOR.get(key, getDefaultMessage());
		return MessageFormat.format(value, getParameters());
	}

	public abstract String getComponentId();

	public abstract String getErrorCode();

	protected abstract String getDefaultMessage();

	protected abstract Object[] getParameters();
}
