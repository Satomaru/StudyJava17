package jp.satomaru.util;

import java.util.Arrays;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import jp.satomaru.util.function.TwoDimConsumer;

/**
 * 2次元の文字配列を作成します。
 *
 * @author Satomaru
 */
public class CharBlockBuilder implements Cloneable {

	/** 改行パターン。 */
	private static final Pattern LINE_SEPARATOR_PATTERN = Pattern.compile("(\r\n|\r|\n)");

	/** 桁数。 */
	private final int width;

	/** 行数。 */
	private final int height;

	/** 文字配列。 */
	private final char[][] block;

	/**
	 * 文字ブロックビルダーを生成します。
	 *
	 * @param width  桁数
	 * @param height 行数
	 */
	public CharBlockBuilder(int width, int height) {
		this.width = Tester.of("width", width).must(Tester.gt(0)).get();
		this.height = Tester.of("height", height).must(Tester.gt(0)).get();
		this.block = new char[height][width];
	}

	/**
	 * 文字ブロックビルダーを複製します。
	 *
	 * @param original 複製する文字ブロックビルダー
	 */
	public CharBlockBuilder(CharBlockBuilder original) {
		width = original.width;
		height = original.height;
		block = original.copy();
	}

	/**
	 * 桁数を取得します。
	 *
	 * @return 桁数
	 */
	public int width() {
		return width;
	}

	/**
	 * 行数を取得します。
	 *
	 * @return 行数
	 */
	public int height() {
		return height;
	}

	/**
	 * 文字配列サイズ (= 桁数 * 行数) を取得します。
	 *
	 * @return 文字配列サイズ (= 桁数 * 行数)
	 */
	public int blockSize() {
		return width * height;
	}

	/**
	 * 文字列変換後のサイズ (= (桁数 + 改行) * 行数) を取得します。
	 *
	 * @return 文字列変換後のサイズ (= (桁数 + 改行) * 行数)
	 */
	public int stringLength() {
		return (width + System.lineSeparator().length()) * height;
	}

	/**
	 * 文字を取得します。
	 *
	 * @param x 桁番号
	 * @param y 行番号
	 * @return 文字
	 */
	public char get(int x, int y) {
		return block[y][x];
	}

	/**
	 * 文字を設定します。
	 *
	 * @param x     桁番号
	 * @param y     行番号
	 * @param value 文字
	 */
	public void set(int x, int y, char value) {
		block[y][x] = value;
	}

	/**
	 * 文字を設定します。
	 *
	 * @param x        桁番号
	 * @param y        行番号
	 * @param operator 現在の文字から、設定する文字を作成する関数
	 */
	public void set(int x, int y, UnaryOperator<Character> operator) {
		set(x, y, operator.apply(get(x, y)));
	}

	/**
	 * 文字配列を設定します。
	 *
	 * @param left   開始桁番号（values[x]の桁番号は、left + x となる）
	 * @param y      行番号
	 * @param values 文字配列
	 */
	public void set(int left, int y, char[] values) {
		System.arraycopy(values, 0, block[y], left, values.length);
	}

	/**
	 * 文字配列を設定します。
	 *
	 * @param left   開始桁番号（values[y][x]の桁番号は、left + x となる）
	 * @param top    開始行番号（values[y][x]の行番号は、top + y となる）
	 * @param values 文字配列
	 */
	public void set(int left, int top, char[][] values) {
		IntStream.range(0, values.length).forEach(index -> {
			set(left, top + index, values[index]);
		});
	}

	/**
	 * 文字列を設定します。
	 *
	 * <p>
	 * 指定された文字列を文字配列に変換してから設定しますが、文字列中に改行が存在する場合は、そこで区切り、以降は次の行とみなします。なお、改行で区切った一行が
	 * {@link #width()} よりも短い場合、その行の以降の文字は変更されません。
	 *
	 * @param left   開始桁番号（改行するまでインクリメントし、改行すると元に戻る）
	 * @param top    開始行番号（改行する毎にインクリメントする）
	 * @param values 文字列
	 */
	public void set(int left, int top, String values) {
		String[] lines = LINE_SEPARATOR_PATTERN.split(values);

		IntStream.range(0, lines.length).forEach(index -> {
			String line = lines[index];
			line.getChars(0, line.length(), block[top + index], left);
		});
	}

	/**
	 * 指定された文字がコード 0 でない場合のみ設定します。
	 *
	 * @param x     桁番号
	 * @param y     行番号
	 * @param value 文字（コード 0 の場合は無視される）
	 */
	public void overlay(int x, int y, char value) {
		if (value != 0) {
			set(x, y, value);
		}
	}

	/**
	 * 指定された文字がコード 0 でない場合のみ設定します。
	 *
	 * @param x        桁番号
	 * @param y        行番号
	 * @param operator 現在の文字から、設定する文字を作成する関数（コード 0 の場合は無視される）
	 */
	public void overlay(int x, int y, UnaryOperator<Character> operator) {
		overlay(x, y, operator.apply(get(x, y)));
	}

	/**
	 * 指定された文字がコード 0 でない場合のみ設定します。
	 *
	 * @param left   開始桁番号（values[x]の桁番号は、left + x となる）
	 * @param y      行番号
	 * @param values 文字配列（コード 0 の場合は無視される）
	 */
	public void overlay(int left, int y, char[] values) {
		IntStream.range(0, values.length).forEach(index -> {
			overlay(left + index, y, values[index]);
		});
	}

	/**
	 * 指定された文字がコード 0 でない場合のみ設定します。
	 *
	 * @param left   開始桁番号（values[y][x]の桁番号は、left + x となる）
	 * @param top    開始行番号（values[y][x]の行番号は、top + y となる）
	 * @param values 文字配列（コード 0 の場合は無視される）
	 */
	public void overlay(int left, int top, char[][] values) {
		IntStream.range(0, values.length).forEach(index -> {
			overlay(left, top + index, values[index]);
		});
	}

	/**
	 * 全ての文字を取得します。
	 *
	 * @param consumer 列番号、行番号、文字を受け取る関数
	 */
	public void forEach(TwoDimConsumer<Character> consumer) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				consumer.accept(x, y, get(x, y));
			}
		}
	}

	/**
	 * 文字配列の全てに同じ文字を設定します。
	 *
	 * @param value 設定する文字
	 */
	public void fill(char value) {
		Arrays.stream(block).forEach(row -> Arrays.fill(row, value));
	}

	/**
	 * 文字配列を複製します。
	 *
	 * @return 複製された文字配列
	 */
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
