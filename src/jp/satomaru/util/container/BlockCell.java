package jp.satomaru.util.container;

import jp.satomaru.util.coordinate.IntPoint;

/**
 * ブロック配列のセルを表します。
 *
 * @author Satomaru
 * @param <T> 内部配列の値
 */
public final class BlockCell<T> {

	/**
	 * ブロックセルの座標および値を受け取る関数です。
	 *
	 * @author Satomaru
	 * @param <T> 内部配列の値
	 */
	@FunctionalInterface
	public interface ExtractedConsumer<T> {

		/**
		 * 座標および値を受け取ります。
		 *
		 * @param x 横座標
		 * @param y 縦座標
		 * @param value 値
		 */
		void accept(int x, int y, T value);

		/**
		 * ブロックセルを受け取ります。
		 *
		 * @param cell ブロックセル
		 */
		default void accept(BlockCell<T> cell) {
			accept(cell.point.x(), cell.point.y(), cell.get());
		}
	}

	/**
	 * ブロックセルの座標および値を受け取り、値を作成する関数です。
	 *
	 * @author Satomaru
	 * @param <T> 内部配列の値
	 */
	@FunctionalInterface
	public interface ExtractedOperator<T> {

		/**
		 * 座標および値を受け取り、値を作成します。
		 *
		 * @param x 横座標
		 * @param y 縦座標
		 * @param value 値
		 * @return 値
		 */
		T apply(int x, int y, T value);

		/**
		 * ブロックセルを受け取り、値を作成します。
		 *
		 * @param cell ブロックセル
		 * @return 値
		 */
		default T apply(BlockCell<T> cell) {
			return apply(cell.point.x(), cell.point.y(), cell.get());
		}
	}

	/** ブロック配列。 */
	private final BlockArray<T> parent;

	/** 座標。 */
	private final IntPoint point;

	/**
	 * ブロックセルを作成します。
	 *
	 * @param parent ブロック配列
	 * @param point 座標
	 */
	public BlockCell(BlockArray<T> parent, IntPoint point) {
		this.parent = parent;
		this.point = parent.rectangle().validate(point);
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
	 * 座標を取得します。
	 *
	 * @return 座標
	 */
	public IntPoint point() {
		return point;
	}

	/**
	 * 値を取得します。
	 *
	 * @return 値
	 */
	public T get() {
		return parent.get(point);
	}

	/**
	 * 関数を実行します。
	 *
	 * @param action 関数
	 */
	public void get(ExtractedConsumer<T> action) {
		action.accept(this);
	}

	/**
	 * 値を設定します。
	 *
	 * @param value 値
	 */
	public void set(T value) {
		parent.set(point, value);
	}

	/**
	 * 関数を実行して、結果を値に設定します。
	 *
	 * @param action 関数
	 */
	public void set(ExtractedOperator<T> action) {
		set(action.apply(this));
	}

	/**
	 * 値をフォーマットします。
	 *
	 * @param format フォーマット ({@link java.util.Formatter} と同じ)
	 * @return フォーマットされた文字列
	 */
	public String format(String format) {
		return String.format(format, get());
	}

	/**
	 * 指定されたブロックセルと値を交換します。
	 *
	 * @param other ブロックセル
	 */
	public void swap(BlockCell<T> other) {
		T otherValue = other.get();
		other.set(get());
		set(otherValue);
	}
}
