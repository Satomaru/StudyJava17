package jp.satomaru.util.console;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.regex.Pattern;

public final class Std {

	public static final StdOut out = new StdOut();
	public static final StdIn in = new StdIn();

	public static final class StdIn {

		private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

		private static final Pattern SSV_DELIMITER = Pattern.compile("\\s+");

		private StdIn() {
		}

		public String readLine(Object... prompts) {
			out.write(prompts);

			try {
				return READER.readLine();
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}

		public List<String> readSsv(Object... prompts) {
			String line = readLine(prompts).trim();

			if (line.isEmpty()) {
				return List.of();
			}

			return List.of(SSV_DELIMITER.split(line));
		}
	}

	public static final class StdOut {

		private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

		private static void writeObjects(Object... objects) {
			try {
				for (Object object : objects) {
					WRITER.write(String.valueOf(object));
				}
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}

		private static void joinObjects(Object[] objects, String delimiter) {
			if (objects.length == 0) {
				return;
			}

			writeObjects(objects[0]);

			for (int index = 1; index < objects.length; index++) {
				writeObjects(delimiter, objects[index]);
			}
		}

		private static void newLine() {
			try {
				WRITER.newLine();
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}

		private static void flush() {
			try {
				WRITER.flush();
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}

		private StdOut() {
		}

		public StdOut write(Object... objects) {
			writeObjects(objects);
			flush();
			return this;
		}

		public StdOut writeLine(Object... objects) {
			writeObjects(objects);
			newLine();
			flush();
			return this;
		}

		public StdOut writeLine() {
			newLine();
			flush();
			return this;
		}

		public StdOut join(Object[] objects, String delimiter) {
			joinObjects(objects, delimiter);
			flush();
			return this;
		}

		public StdOut joinLine(Object[] objects, String delimiter) {
			joinObjects(objects, delimiter);
			newLine();
			flush();
			return this;
		}

		public StdOut format(String pattern, Object... objects) {
			writeObjects(String.format(pattern, objects));
			flush();
			return this;
		}

		public StdOut formatLine(String pattern, Object... objects) {
			writeObjects(String.format(pattern, objects));
			newLine();
			flush();
			return this;
		}
	}
}
