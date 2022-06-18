package jp.satomaru.util;

public abstract class LocalizedException extends Exception {

	private static final long serialVersionUID = 1L;

	public LocalizedException() {
		super();
	}

	public LocalizedException(String message) {
		super(message);
	}

	public LocalizedException(Throwable cause) {
		super(cause);
	}

	public LocalizedException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public final String getLocalizedMessage() {
		String localized = readLocalizedMessage();
		return (localized != null) ? localized : getMessage();
	}

	protected abstract String readLocalizedMessage();
}
