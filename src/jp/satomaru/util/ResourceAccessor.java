package jp.satomaru.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public final class ResourceAccessor {

	public record Key(Class<?> owner, String id, String subkey) {
		public String withId() {
			return Strings.join(owner.getName(), "[", id, "].", subkey);
		}

		public String withoutId() {
			return Strings.join(owner.getName(), ".", subkey);
		}
	}

	private static final Pattern PACKAGE_DELIMITER = Pattern.compile("\\.");

	public static ResourceAccessor of(ResourceBundle bundle) {
		return new ResourceAccessor(bundle);
	}

	public static ResourceAccessor of(Package location, String title) {
		String path = PACKAGE_DELIMITER.matcher(location.getName()).replaceAll("/");
		String baseName = Strings.join(path, "/", title);
		ResourceBundle bundle;

		try {
			bundle = ResourceBundle.getBundle(baseName);
		} catch (MissingResourceException e) {
			bundle = null;
		}

		return new ResourceAccessor(bundle);
	}

	private final ResourceBundle bundle;

	private ResourceAccessor(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public String get(String key, String whenMissing) {
		if (bundle == null) {
			return whenMissing;
		}

		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return whenMissing;
		}
	}

	public String get(String key) {
		return get(key, null);
	}

	public String get(Key key, String whenMissing) {
		String valueWithId = get(key.withId());

		if (valueWithId != null) {
			return valueWithId;
		}

		String valueWithoutId = get(key.withoutId());

		if (valueWithoutId != null) {
			return valueWithoutId;
		}

		return whenMissing;
	}

	public String get(Key key) {
		return get(key, null);
	}
}
