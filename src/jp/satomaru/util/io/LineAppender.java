package jp.satomaru.util.io;

import java.io.IOException;
import java.io.PrintStream;
import java.util.function.Supplier;

public class LineAppender<A extends Appendable> {

	public static final Supplier<String> EMPTY = () -> "";

	public static LineAppender<PrintStream> sysout() {
		return new LineAppender<>(System.out);
	}

	public static LineAppender<StringBuilder> builder() {
		return new LineAppender<>(new StringBuilder());
	}

	private final A appendable;

	private final IOSilencer<A> silencer;

	private Supplier<String> lineBegin = EMPTY;

	private Supplier<String> delimiter = EMPTY;

	private Supplier<String> lineEnd = System::lineSeparator;

	private boolean returned = true;

	public LineAppender(A appendable) {
		this.appendable = appendable;
		this.silencer = new IOSilencer<>(appendable);
	}

	public A getAppendable() {
		return appendable;
	}

	public boolean isThrown() {
		return silencer.isThrown();
	}

	public IOException getException() {
		return silencer.getException();
	}

	public void setLineBegin(Supplier<String> lineBegin) {
		this.lineBegin = lineBegin;
	}

	public void setDelimiter(Supplier<String> delimiter) {
		this.delimiter = delimiter;
	}

	public void setLineEnd(Supplier<String> lineEnd) {
		this.lineEnd = lineEnd;
	}

	public LineAppender<A> newLine() {
		silencer.write(Appendable::append, lineEnd.get());
		returned = true;
		return this;
	}

	public LineAppender<A> write(Object... phrases) {
		if (returned) {
			silencer.write(Appendable::append, lineBegin.get());
			returned = false;
		} else {
			silencer.write(Appendable::append, delimiter.get());
		}

		for (Object phrase : phrases) {
			silencer.write(Appendable::append, String.valueOf(phrase));
		}

		return this;
	}

	public LineAppender<A> writeLine(Object... phrases) {
		return write(phrases).newLine();
	}

	public LineAppender<A> format(String format, Object... args) {
		return write(String.format(format, args));
	}

	public LineAppender<A> orElse(Object value, Object whenNull) {
		return write((value != null) ? value : whenNull);
	}

	public LineAppender<A> localizedMessage(Throwable throwable) {
		return write(throwable.getLocalizedMessage());
	}
}
