package jp.satomaru.util;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import jp.satomaru.util.function.TwoDimOperator;

public class CharBlockBuilder implements Cloneable {

	private static final Pattern LINE_SEPARATOR_PATTERN = Pattern.compile("(\r\n|\r|\n)");

	private final int width;

	private final int height;

	private final char[][] block;

	public CharBlockBuilder(int width, int height) {
		this.width = width;
		this.height = height;
		this.block = new char[height][width];
	}

	public CharBlockBuilder(CharBlockBuilder original) {
		width = original.width;
		height = original.height;
		block = original.copy();
	}

	public int width() {
		return width;
	}

	public int height() {
		return height;
	}

	public char[][] block() {
		return block;
	}

	public int blockSize() {
		return width * height;
	}

	public int stringLength() {
		return (width + System.lineSeparator().length()) * height;
	}

	public char get(int x, int y) {
		return block[y][x];
	}

	public void set(int x, int y, char value) {
		block[y][x] = value;
	}

	public void set(int x, int y, TwoDimOperator<Character> operator) {
		set(x, y, operator.apply(x, y, get(x, y)));
	}

	public void set(int left, int y, char[] values) {
		System.arraycopy(values, 0, block[y], left, values.length);
	}

	public void set(int left, int top, char[][] values) {
		IntStream.range(0, values.length).parallel().forEach(index -> {
			set(left, top + index, values[index]);
		});
	}

	public void set(int left, int top, String values) {
		String[] lines = LINE_SEPARATOR_PATTERN.split(values);

		IntStream.range(0, lines.length).parallel().forEach(index -> {
			String line = lines[index];
			line.getChars(0, line.length(), block[top + index], left);
		});
	}

	public void overlay(int x, int y, char value) {
		if (value != 0) {
			set(x, y, value);
		}
	}

	public void overlay(int x, int y, TwoDimOperator<Character> operator) {
		overlay(x, y, operator.apply(x, y, get(x, y)));
	}

	public void overlay(int left, int y, char[] values) {
		IntStream.range(0, values.length).parallel().forEach(index -> {
			overlay(left + index, y, values[index]);
		});
	}

	public void overlay(int left, int top, char[][] values) {
		IntStream.range(0, values.length).parallel().forEach(index -> {
			overlay(left, top + index, values[index]);
		});
	}

	public void fill(char value) {
		Arrays.stream(block).parallel().forEach(row -> Arrays.fill(row, value));
	}

	public char[][] copy() {
		return Arrays.stream(block).map(row -> Arrays.copyOf(row, width)).toArray(char[][]::new);
	}

	@Override
	public CharBlockBuilder clone() {
		return new CharBlockBuilder(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(stringLength());
		Arrays.stream(block).forEach(row -> builder.append(row).append(System.lineSeparator()));
		return builder.toString();
	}
}
