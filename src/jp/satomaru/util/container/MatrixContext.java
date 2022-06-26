package jp.satomaru.util.container;

import java.util.function.IntFunction;
import java.util.stream.IntStream;

import jp.satomaru.util.Tester;

/**
 * マトリックスの情報を保持します。
 *
 * @author Satomaru
 *
 * @param <T> セルが持つ値
 */
public final class MatrixContext<T> {

	/** 列数。 */
	private final int width;

	/** 行数。 */
	private final int height;

	/** 値配列。 */
	private final T[] values;

	MatrixContext(int width, int height, IntFunction<T[]> constructor) {
		this.width = Tester.must("width", width, Tester.gt(0));
		this.height = Tester.must("height", height, Tester.gt(0));
		this.values = constructor.apply(width * height);
	}

	/**
	 * 列数を取得します。
	 *
	 * @return 列数
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
	 * 列番号を検査します。
	 *
	 * @param x 列番号
	 * @return 検査した列番号
	 */
	public int testX(int x) {
		return Tester.must("x", x, Tester.range(0, width));
	}

	/**
	 * 行番号を検査します。
	 *
	 * @param y 行番号
	 * @return 検査した行番号
	 */
	public int textY(int y) {
		return Tester.must("y", y, Tester.range(0, height));
	}

	/**
	 * 値を取得します。
	 *
	 * @param x 列番号
	 * @param y 行番号
	 * @return 値
	 */
	public T get(int x, int y) {
		return values[x + y * width];
	}

	/**
	 * 値を設定します。
	 *
	 * @param x     列番号
	 * @param y     行番号
	 * @param value 値
	 */
	public void set(int x, int y, T value) {
		values[x + y * width] = value;
	}

	/**
	 * 0 から 列数 - 1 までの整数ストリームを作成します。
	 *
	 * @return 0 から 列数 - 1 までの整数ストリーム
	 */
	public IntStream colRange() {
		return IntStream.range(0, width);
	}

	/**
	 * 0 から 行数 - 1 までの整数ストリームを作成します。
	 *
	 * @return 0 から 列数 - 1 までの整数ストリーム
	 */
	public IntStream rowRange() {
		return IntStream.range(0, height);
	}
}
