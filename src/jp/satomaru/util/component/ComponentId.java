package jp.satomaru.util.component;

import jp.satomaru.util.Strings;

public record ComponentId(Class<?> type, String name) {

	public String key() {
		return Strings.join(type.getName(), "[", name, "]");
	}

	public String key(String subkey) {
		return Strings.join(type.getName(), "[", name, "].", subkey);
	}

	public String keyWithoutName(String subkey) {
		return Strings.join(type.getName(), ".", subkey);
	}

	public ComponentId rename(String newName) {
		return new ComponentId(type, newName);
	}
}
