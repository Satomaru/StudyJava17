package jp.satomaru.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public final class ResourceAccessor {

	public record Key(Class<?> type, String name, String subkey, String defaultMessae) {

		public String base() {
			return Strings.join(type.getName(), "[", name, "]");
		}

		public String full() {
			return Strings.join(type.getName(), "[", name, "].", subkey);
		}

		public String common() {
			return Strings.join(type.getName(), ".", subkey);
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

	public Optional<String> get(String key) {
		return Optional.ofNullable(get(key, null));
	}

	public String get(Key key, Object... params) {
		String caption = get(key.base()).orElse(key.name);
		String message = get(key.full()).or(() -> get(key.common())).orElse(key.defaultMessae);
		ArrayList<Object> args = new ArrayList<>(params.length + 1);
		args.add(caption);
		Collections.addAll(args, params);
		return MessageFormat.format(message, args.toArray());
	}
}
