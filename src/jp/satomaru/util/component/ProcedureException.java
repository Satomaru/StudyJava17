package jp.satomaru.util.component;

import jp.satomaru.util.Strings;

public final class ProcedureException extends ComponentException {

	private static final long serialVersionUID = 1L;

	private final String procedureName;

	private final String errorCode;

	public ProcedureException(String procedureName, String errorCode) {
		super();
		this.procedureName = procedureName;
		this.errorCode = errorCode;
	}

	public ProcedureException(String procedureName, String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.procedureName = procedureName;
		this.errorCode = errorCode;
	}

	public ProcedureException(String procedureName, String errorCode, String message) {
		super(message);
		this.procedureName = procedureName;
		this.errorCode = errorCode;
	}

	public ProcedureException(String procedureName, String errorCode, Throwable cause) {
		super(cause);
		this.procedureName = procedureName;
		this.errorCode = errorCode;
	}

	@Override
	public String getComponentId() {
		return procedureName;
	}

	@Override
	public String getErrorCode() {
		return errorCode;
	}

	@Override
	protected String getDefaultMessage() {
		return Strings.join("error {1} at {0}");
	}

	@Override
	protected Object[] getParameters() {
		return new Object[] {
			procedureName,
			errorCode
		};
	}
}
