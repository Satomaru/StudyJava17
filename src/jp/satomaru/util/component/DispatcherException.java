package jp.satomaru.util.component;

/**
 * ディスパッチャーに関する例外です。
 *
 * @author Satomaru
 */
public final class DispatcherException extends ComponentException {

	private static final long serialVersionUID = 1L;

	public enum ErrorCode {
		EMPTY_COMMAND("{0} is required"),
		UNKNOWN_COMMAND("unknown {0}: {1}");

		private final String defaultMessage;

		private ErrorCode(String defaultMessage) {
			this.defaultMessage = defaultMessage;
		}
	}

	private final String commandName;

	private final ErrorCode errorCode;

	private final Object command;

	public DispatcherException(String commandName, ErrorCode errorCode, Object command) {
		super();
		this.commandName = commandName;
		this.errorCode = errorCode;
		this.command = command;
	}

	@Override
	public String getComponentId() {
		return commandName;
	}

	@Override
	public String getErrorCode() {
		return errorCode.name();
	}

	public Object getCommand() {
		return command;
	}

	@Override
	protected String getDefaultMessage() {
		return errorCode.defaultMessage;
	}

	@Override
	protected Object[] getParameters() {
		return new Object[] {
			commandName,
			command
		};
	}
}
