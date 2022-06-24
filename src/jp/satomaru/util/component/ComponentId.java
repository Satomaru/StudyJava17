package jp.satomaru.util.component;

public record ComponentId(Class<?> type, String name) {

	public ComponentId rename(String newName) {
		return new ComponentId(type, newName);
	}
}
