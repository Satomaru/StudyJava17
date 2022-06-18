package jp.satomaru.util.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Pattern;

public final class StdIn {

	private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

	private static final Pattern SSV_DELIMITER = Pattern.compile("\\s+");

	public static String readLine(Object... prompts) {
		StdOut.write(prompts);

		try {
			return READER.readLine();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public static List<String> readSsv(Object... prompts) {
		String line = readLine(prompts).trim();

		if (line.isEmpty()) {
			return List.of();
		}

		return List.of(SSV_DELIMITER.split(line));
	}

	private StdIn() {
	}
}
