package jp.satomaru.util.container;

import java.util.stream.Stream;

import jp.satomaru.util.coordinate.IntRectangle;

/**
 * ブロック配列の行を表します。
 *
 * @author Satomaru
 * @param <T> 内部配列の値
 */
public final class BlockRow<T>
	implements BlockCellOperator<T> {

	/** ブロック配列。 */
	private final BlockArray<T> parent;

	/** 縦座標。 */
	private final int y;

	/** 座標セット。 */
	private final IntRectangle rectangle;

	/**
	 * ブロック行を作成します。
	 *
	 * @param parent ブロック配列
	 * @param y 縦座標
	 */
	public BlockRow(BlockArray<T> parent, int y) {
		this.parent = parent;
		this.y = parent.rectangle().height().validate(y);
		this.rectangle = parent.rectangle().row(y);
	}

	/**
	 * ブロック配列を取得します。
	 *
	 * @return ブロック配列
	 */
	public BlockArray<T> parent() {
		return parent;
	}

	/**
	 * 縦座標を取得します。
	 *
	 * @return 縦座標
	 */
	public int y() {
		return y;
	}

	/**
	 * ブロックセルを取得します。
	 *
	 * @param x 横座標
	 * @return ブロックセル
	 */
	public BlockCell<T> cell(int x) {
		return parent.cell(x, y);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IntRectangle rectangle() {
		return rectangle;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Stream<BlockCell<T>> cells() {
		return rectangle.stream().map(point -> new BlockCell<T>(parent, point));
	}
}
