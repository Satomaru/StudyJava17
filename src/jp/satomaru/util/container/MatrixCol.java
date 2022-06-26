package jp.satomaru.util.container;

import java.util.stream.Stream;

/**
 * マトリックスの列。
 *
 * @author Satomaru
 *
 * @param <T> セルが持つ値
 */
public final class MatrixCol<T> implements MatrixCellOperator<T> {

	/** コンテキスト。 */
	private final MatrixContext<T> context;

	/** 列番号。 */
	private final int x;

	MatrixCol(MatrixContext<T> context, int x) {
		this.context = context;
		this.x = context.testX(x);
	}

	/**
	 * 列番号を取得します。
	 *
	 * @return 列番号
	 */
	public int x() {
		return x;
	}

	/**
	 * セルを取得します。
	 *
	 * @param y 行番号
	 * @return セル
	 */
	public MatrixCell<T> cell(int y) {
		return new MatrixCell<>(context, x, y);
	}

	/**
	 * セルを取得します。
	 *
	 * @return セル
	 */
	@Override
	public Stream<MatrixCell<T>> cells() {
		return context.rowRange().mapToObj(this::cell);
	}
}
