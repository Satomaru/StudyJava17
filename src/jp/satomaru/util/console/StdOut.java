package jp.satomaru.util.console;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public final class StdOut {

	private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void write(Object... objects) {
		try {
			for (Object object : objects) {
				WRITER.write(String.valueOf(object));
			}

			WRITER.flush();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public static void writeLine(Object... objects) {
		write(objects);

		try {
			WRITER.newLine();
			WRITER.flush();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private StdOut() {
	}
}
