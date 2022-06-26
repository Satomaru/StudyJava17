package jp.satomaru.util.container;

import java.util.function.IntFunction;
import java.util.stream.Stream;

/**
 * 2次元の配列。
 *
 * @author Satomaru
 *
 * @param <T> セルが持つ値
 */
public final class Matrix<T> implements MatrixCellOperator<T> {

	/**
	 * マトリックスを作成します。
	 *
	 * @param width       列数
	 * @param height      行数
	 * @param constructor 1次元配列のコンストラクタ
	 * @return
	 */
	public static <T> Matrix<T> of(int width, int height, IntFunction<T[]> constructor) {
		return new Matrix<>(new MatrixContext<>(width, height, constructor));
	}

	/**
	 * 整数のマトリックスを作成します。
	 *
	 * @param width  列数
	 * @param height 行数
	 * @return 整数のマトリックス
	 */
	public static Matrix<Integer> ofInteger(int width, int height) {
		return new Matrix<>(new MatrixContext<>(width, height, Integer[]::new));
	}

	/** コンテキスト。 */
	private final MatrixContext<T> context;

	private Matrix(MatrixContext<T> context) {
		this.context = context;
	}

	/**
	 * 列数を取得します。
	 *
	 * @return 列数
	 */
	public int width() {
		return context.width();
	}

	/**
	 * 行数を取得します。
	 *
	 * @return 行数
	 */
	public int height() {
		return context.height();
	}

	/**
	 * 行を取得します。
	 *
	 * @param y 行番号
	 * @return 行
	 */
	public MatrixRow<T> row(int y) {
		return new MatrixRow<>(context, y);
	}

	/**
	 * 行を取得します。
	 *
	 * @return 行
	 */
	public Stream<MatrixRow<T>> rows() {
		return context.rowRange().mapToObj(this::row);
	}

	/**
	 * 列を取得します。
	 *
	 * @param x 列番号
	 * @return 列
	 */
	public MatrixCol<T> col(int x) {
		return new MatrixCol<>(context, x);
	}

	/**
	 * 列を取得します。
	 *
	 * @return 列
	 */
	public Stream<MatrixCol<T>> cols() {
		return context.colRange().mapToObj(this::col);
	}

	/**
	 * セルを取得します。
	 *
	 * @param x 列番号
	 * @param y 行番号
	 * @return セル
	 */
	public MatrixCell<T> cell(int x, int y) {
		return new MatrixCell<>(context, x, y);
	}

	/**
	 * セルを取得します。
	 *
	 * @return セル
	 */
	@Override
	public Stream<MatrixCell<T>> cells() {
		return rows().flatMap(MatrixRow::cells);
	}
}
