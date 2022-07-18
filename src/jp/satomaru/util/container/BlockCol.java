package jp.satomaru.util.container;

import java.util.stream.Stream;

import jp.satomaru.util.coordinate.IntRectangle;

/**
 * ブロック配列の列を表します。
 *
 * @author Satomaru
 * @param <T> 内部配列の値
 */
public final class BlockCol<T>
	implements BlockCellOperator<T> {

	/** ブロック配列。 */
	private final BlockArray<T> parent;

	/** 横座標。 */
	private final int x;

	/** 座標セット。 */
	private final IntRectangle rectangle;

	/**
	 * ブロック列を作成します。
	 *
	 * @param parent ブロック配列
	 * @param x 横座標
	 */
	public BlockCol(BlockArray<T> parent, int x) {
		this.parent = parent;
		this.x = parent.rectangle().width().validate(x);
		this.rectangle = parent.rectangle().col(x);
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
	 * 横座標を取得します。
	 *
	 * @return 横座標
	 */
	public int x() {
		return x;
	}

	/**
	 * ブロックセルを取得します。
	 *
	 * @param y 縦座標
	 * @return ブロックセル
	 */
	public BlockCell<T> cell(int y) {
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
