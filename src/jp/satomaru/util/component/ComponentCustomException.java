package jp.satomaru.util.component;

public class ComponentCustomException extends ComponentException {

	private static final long serialVersionUID = 1L;

	private final String customCode;

	public ComponentCustomException(ComponentId id, String customCode, Object value) {
		super(id, ErrorCode.FAILED, value);
		this.customCode = customCode;
	}

	public String getCustomCode() {
		return customCode;
	}

	@Override
	protected String getMessaggeSubkey() {
		return customCode;
	}
}
