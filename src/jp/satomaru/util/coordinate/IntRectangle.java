package jp.satomaru.util.coordinate;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Stream;

import jp.satomaru.util.SequentialIdSet;

/**
 * 2次元矩形の整数座標セットです。
 *
 * @param width 横のリニア
 * @param height 縦のリニア
 * @author Satomaru
 */
public record IntRectangle(IntLinear width, IntLinear height)
	implements SequentialIdSet<IntPoint, IntRectangle> {

	/**
	 * レクタングルを作成します。
	 *
	 * <p>
	 * 幅および高さが1未満の場合は {@link IllegalArgumentException} がスローされます。
	 *
	 * @param width 幅 (0から幅-1までのリニアとなる)
	 * @param height 高さ (0から高さ-1までのリニアとなる)
	 * @return レクタングル
	 */
	public static IntRectangle of(int width, int height) {
		return new IntRectangle(IntLinear.of(width), IntLinear.of(height));
	}

	/**
	 * レクタングルを作成します。
	 *
	 * @param width 横のリニア
	 * @param height 縦のリニア
	 */
	public IntRectangle {
		Objects.requireNonNull(width, "width");
		Objects.requireNonNull(height, "height");
	}

	/**
	 * 指定された横座標だけを切り取ったレクタングルを取得します。
	 *
	 * <p>
	 * 指定された横座標が存在しない場合は
	 * {@link NoSuchElementException} をスローします。
	 *
	 * @param x 横座標
	 * @return 指定された横座標だけを切り取ったレクタングル
	 */
	public IntRectangle col(int x) {
		return new IntRectangle(width.part(x, x), height);
	}

	/**
	 * 同一の横座標だけを切り取ったレクタングルのストリームを取得します。
	 *
	 * @return 同一の横座標だけを切り取ったレクタングルのストリーム (昇順)
	 */
	public Stream<IntRectangle> cols() {
		return width.stream().map(this::col);
	}

	/**
	 * 指定された縦座標だけを切り取ったレクタングルを取得します。
	 *
	 * <p>
	 * 指定された縦座標が存在しない場合は
	 * {@link NoSuchElementException} をスローします。
	 *
	 * @param y 縦座標
	 * @return 指定された縦座標だけを切り取ったレクタングル
	 */
	public IntRectangle row(int y) {
		return new IntRectangle(width, height.part(y, y));
	}

	/**
	 * 同一の縦座標だけを切り取ったレクタングルのストリームを取得します。
	 *
	 * @return 同一の縦座標だけを切り取ったレクタングルのストリーム (昇順)
	 */
	public Stream<IntRectangle> rows() {
		return height.stream().map(this::row);
	}

	/**
	 * 座標を取得します。
	 *
	 * <p>
	 * 指定された座標が存在しない場合は
	 * {@link NoSuchElementException} をスローします。
	 *
	 * @param x 横座標
	 * @param y 縦座標
	 * @return 座標
	 */
	public IntPoint point(int x, int y) {
		return new IntPoint(width.validate(x), height.validate(y));
	}

	public int colIndex(IntPoint point) {
		return width.seek(point.x());
	}

	public int rowIndex(IntPoint point) {
		return height.seek(point.y());
	}

	/**
	 * 座標の総数の取得します。
	 *
	 * @return 座標の総数
	 */
	@Override
	public int size() {
		return width.size() * height.size();
	}

	/**
	 * 座標のインデックスを数えます。
	 *
	 * <p>
	 * インデックスは必ず、0以上 {@link #size()} 未満となります。
	 * なお、指定された座標が存在しない場合は、
	 * {@link NoSuchElementException} がスローされます。
	 *
	 * @param point 座標
	 * @return インデックス
	 */
	@Override
	public int seek(IntPoint point) {
		int indexX = width.seek(point.x());
		int indexY = height.seek(point.y());

		if (indexX == -1 || indexY == -1) {
			throw new NoSuchElementException();
		}

		return indexX + indexY * width.size();
	}

	/**
	 * 座標を取得します。
	 *
	 * <p>
	 * インデックスに、0未満、または {@link #size()} 以上が指定された場合は
	 * {@link NoSuchElementException} がスローされます。
	 *
	 * @param index インデックス
	 * @return 座標
	 */
	@Override
	public IntPoint pick(int index) {
		if (index < 0 || index >= size()) {
			return null;
		}

		int x = width.pick(index % width.size());
		int y = height.pick(index / width.size());
		return new IntPoint(x, y);
	}

	/**
	 * 指定された座標が含まれていることを判定します。
	 *
	 * @param point 座標
	 * @return 座標が含まれている場合はtrue
	 */
	@Override
	public boolean contains(IntPoint point) {
		return width.contains(point.x()) && height.contains(point.y());
	}

	/**
	 * 指定されたレクタングルの座標が全て含まれていることを判定します。
	 *
	 * @param rectangle レクタングル
	 * @return 座標が全て含まれている場合はtrue
	 */
	@Override
	public boolean contains(IntRectangle rectangle) {
		return width.contains(rectangle.width) && height.contains(rectangle.height);
	}
}
