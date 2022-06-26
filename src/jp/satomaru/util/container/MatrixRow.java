package jp.satomaru.util.container;

import java.util.stream.Stream;

/**
 * マトリックスの行。
 *
 * @author Satomaru
 *
 * @param <T> セルが持つ値
 */
public final class MatrixRow<T> implements MatrixCellOperator<T> {

	/** コンテキスト。 */
	private final MatrixContext<T> context;

	/** 行番号。 */
	private final int y;

	MatrixRow(MatrixContext<T> context, int y) {
		this.context = context;
		this.y = context.textY(y);
	}

	/**
	 * 行番号を取得します。
	 *
	 * @return 行番号
	 */
	public int y() {
		return y;
	}

	/**
	 * セルを取得します。
	 *
	 * @param y 行番号
	 * @return セル
	 */
	public MatrixCell<T> cell(int x) {
		return new MatrixCell<>(context, x, y);
	}

	/**
	 * セルを取得します。
	 *
	 * @return セル
	 */
	@Override
	public Stream<MatrixCell<T>> cells() {
		return context.colRange().mapToObj(this::cell);
	}
}
