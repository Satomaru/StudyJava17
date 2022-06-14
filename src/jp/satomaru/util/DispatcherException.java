package jp.satomaru.util;

/**
 * ディスパッチャーに関する例外です。
 *
 * @author Satomaru
 */
public class DispatcherException extends Exception {

	private static final long serialVersionUID = 1L;

	/** 例外の種類。 */
	public enum Type {
		EMPTY_COMMAND("command is required"),
		UNKNOWN_COMMAND("unknown command: %1$s");

		private final String message;

		private Type(final String message) {
			this.message = message;
		}

		/**
		 * メッセージを取得します。
		 *
		 * @param commadn0
		 * @return メッセージ
		 */
		public String getMessage(Object command) {
			return String.format(message, command);
		}
	}

	/** コマンド。 */
	private final Object command;

	/** 例外の種類。 */
	private final Type type;

	/**
	 * ディスパッチャーに関する例外を生成します。 0
	 *
	 * @param command コマンド
	 * @param type    例外の種類
	 */
	public DispatcherException(Object command, Type type) {
		super(type.getMessage(command));
		this.command = command;
		this.type = type;
	}

	/**
	 * コマンドを取得します。
	 *
	 * @return コマンド
	 */
	public Object getCommand() {
		return command;
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
