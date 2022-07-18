package jp.satomaru.util;

import java.util.regex.Pattern;

public final class Strings {

	public static final Pattern LINE_DLIMITER_PATTERN = Pattern.compile("(\\r\\n|\\r|\\n)");

	public static String join(Object... objects) {
		return switch (objects.length) {
			case 0 -> "";
			case 1 -> String.valueOf(objects[0]);
			case 2 -> String.valueOf(objects[0]).concat(String.valueOf(objects[1]));
			default -> joinThreeOrMore(objects);
		};
	}

	private static String joinThreeOrMore(Object[] objects) {
		String[] strings = new String[objects.length];
		int totalLength = 0;

		for (int index = 0; index < objects.length; index++) {
			strings[index] = String.valueOf(objects[index]);
			totalLength += strings[index].length();
		}

		if (totalLength == 0) {
			return "";
		}

		char[] buffer = new char[totalLength];
		int position = 0;

		for (String string : strings) {
			int length = string.length();

			if (length != 0) {
				string.getChars(0, length, buffer, position);
				position += length;
			}
		}

		return new String(buffer);
	}

	public static Character[] charArray(String string) {
		return string.chars()
			.mapToObj(e -> (char) e)
			.toArray(Character[]::new);
	}

	public static Character[][] charBlock(String string) {
		return LINE_DLIMITER_PATTERN.splitAsStream(string)
			.map(Strings::charArray)
			.toArray(Character[][]::new);
	}

	private Strings() {
	}
}
