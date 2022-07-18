package jp.satomaru.util.container;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import jp.satomaru.util.coordinate.IntPoint;
import jp.satomaru.util.coordinate.IntRectangle;

/**
 * 座標セットを用いて、疑似的な2次元配列を実現します。
 *
 * @author Satomaru
 * @param <T> 内部配列の値
 */
public final class BlockArray<T>
	implements Cloneable, BlockCellOperator<T> {

	/**
	 * ブロック配列を作成します。
	 *
	 * @param <T> 内部配列の値
	 * @param rectangle 座標セット
	 * @param generator 内部配列を作成する関数
	 * @param initial 要素の初期値
	 * @return ブロック配列
	 */
	public static <T> BlockArray<T> of(IntRectangle rectangle, IntFunction<T[]> generator, T initial) {
		T[] array = generator.apply(rectangle.size());

		if (initial != null) {
			Arrays.fill(array, initial);
		}

		return new BlockArray<>(rectangle, array);
	}

	/**
	 * ブロック配列を作成します。
	 *
	 * @param <T> 内部配列の値
	 * @param width 座標セットの幅 (座標は0..幅-1となる)
	 * @param height 座標セットの高さ (座標は0..高さ-1となる)
	 * @param generator 内部配列を作成する関数
	 * @param initial 要素の初期値
	 * @return ブロック配列
	 */
	public static <T> BlockArray<T> of(int width, int height, IntFunction<T[]> generator, T initial) {
		return of(IntRectangle.of(width, height), generator, initial);
	}

	/**
	 * 整数ブロック配列を作成します。
	 *
	 * <p>
	 * 初期値は 0 となります。
	 *
	 * @param width 座標セットの幅 (座標は0..幅-1となる)
	 * @param height 座標セットの高さ (座標は0..高さ-1となる)
	 * @return 整数ブロック配列
	 */
	public static BlockArray<Integer> integer(int width, int height) {
		return of(width, height, Integer[]::new, 0);
	}

	/**
	 * 文字ブロック配列を作成します。
	 *
	 * <p>
	 * 初期値は半角空白となります。
	 *
	 * @param width 座標セットの幅 (座標は0..幅-1となる)
	 * @param height 座標セットの高さ (座標は0..高さ-1となる)
	 * @return 文字ブロック配列
	 */
	public static BlockArray<Character> character(int width, int height) {
		return of(width, height, Character[]::new, ' ');
	}

	/** 座標セット。 */
	private final IntRectangle rectangle;

	/** 内部配列。 */
	private final T[] array;

	/**
	 * ブロック配列を作成します。
	 *
	 * <p>
	 * 内部配列の要素数は、座標セットの総数と一致している必要があります。
	 *
	 * @param rectangle 座標セット
	 * @param array 内部配列
	 */
	public BlockArray(IntRectangle rectangle, T[] array) {
		if (array.length != rectangle.size()) {
			throw new IllegalArgumentException("array.length != rectangle.size()");
		}

		this.rectangle = rectangle;
		this.array = array;
	}

	/**
	 * 内部配列を取得します。
	 *
	 * @return 内部配列
	 */
	public T[] array() {
		return array;
	}

	/**
	 * 指定された座標の値を取得します。
	 *
	 * @param point 座標
	 * @return 値
	 */
	public T get(IntPoint point) {
		return array[rectangle.seek(point)];
	}

	/**
	 * 指定された座標の値を取得します。
	 *
	 * @param x 縦座標
	 * @param y 横座標
	 * @return 値
	 */
	public T get(int x, int y) {
		return get(rectangle.point(x, y));
	}

	/**
	 * 指定された座標から右に向かって値を取得します。
	 *
	 * @param left 起点となる座標
	 * @param dest 取得した値を格納する配列
	 */
	public void get(IntPoint left, T[] dest) {
		switch (dest.length) {
			case 0:
				break;

			case 1:
				dest[0] = get(left);
				break;

			default:
				IntPoint right = left.move(dest.length - 1, 0);
				rectangle.validate(left);
				rectangle.validate(right);
				System.arraycopy(array, rectangle.seek(left), dest, 0, dest.length);
		}
	}

	/**
	 * 指定された座標から右に向かって値を取得します。
	 *
	 * @param left 起点となる横座標
	 * @param y 縦座標
	 * @param dest 取得した値を格納する配列
	 */
	public void get(int left, int y, T[] dest) {
		get(rectangle.point(left, y), dest);
	}

	/**
	 * 指定された座標から右下に向かって値を取得します。
	 *
	 * @param leftTop 起点となる座標
	 * @param dest 取得した値を格納する配列
	 */
	public void get(IntPoint leftTop, T[][] dest) {
		IntPoint current = leftTop;

		for (T[] destRow : dest) {
			get(current, destRow);
			current = current.down();
		}
	}

	/**
	 * 指定された座標から右下に向かって値を取得します。
	 *
	 * @param left 起点となる横座標
	 * @param top 起点となる縦座標
	 * @param dest 取得した値を格納する配列
	 */
	public void get(int left, int top, T[][] dest) {
		get(rectangle.point(left, top), dest);
	}

	/**
	 * 指定されたブロックセルに、同じ座標の値を格納します。
	 *
	 * @param dest 取得した値を格納するブロックセル
	 */
	public void get(BlockCell<T> dest) {
		dest.set(get(dest.point()));
	}

	/**
	 * 指定されたブロックセルオペレーターに、同じ座標の値を格納します。
	 *
	 * @param dest 取得した値を格納するブロックセルオペレーター
	 */
	public void get(BlockCellOperator<T> dest) {
		dest.cells().forEach(this::get);
	}

	/**
	 * 指定された座標の値を設定します。
	 *
	 * @param point 座標
	 * @param value 値
	 */
	public void set(IntPoint point, T value) {
		array[rectangle.seek(point)] = value;
	}

	/**
	 * 指定された座標の値を設定します。
	 *
	 * @param x 縦座標
	 * @param y 横座標
	 * @param value 値
	 */
	public void set(int x, int y, T value) {
		set(rectangle.point(x, y), value);
	}

	/**
	 * 指定された座標から右に向かって値を設定します。
	 *
	 * @param left 起点となる座標
	 * @param src 設定する値が格納された配列
	 */
	public void set(IntPoint left, T[] src) {
		switch (src.length) {
			case 0:
				break;

			case 1:
				set(left, src[0]);
				break;

			default:
				IntPoint right = left.move(src.length - 1, 0);
				rectangle.validate(left);
				rectangle.validate(right);
				System.arraycopy(src, 0, array, rectangle.seek(left), src.length);
		}
	}

	/**
	 * 指定された座標から右に向かって値を設定します。
	 *
	 * @param left 起点となる横座標
	 * @param y 縦座標
	 * @param src 設定する値が格納された配列
	 */
	public void set(int left, int y, T[] src) {
		set(rectangle.point(left, y), src);
	}

	/**
	 * 指定された座標から右下に向かって値を設定します。
	 *
	 * @param leftTop 起点となる座標
	 * @param src 設定する値が格納された配列
	 */
	public void set(IntPoint leftTop, T[][] src) {
		IntPoint current = leftTop;

		for (T[] srcRow : src) {
			set(current, srcRow);
			current = current.down();
		}
	}

	/**
	 * 指定された座標から右下に向かって値を設定します。
	 *
	 * @param left 起点となる横座標
	 * @param top 起点となる縦座標
	 * @param src 設定する値が格納された配列
	 */
	public void set(int left, int top, T[][] src) {
		set(rectangle.point(left, top), src);
	}

	/**
	 * 指定されたブロックセルから値を取得して、同じ座標に設定します。
	 *
	 * @param src 設定する値が格納されたブロックセル
	 */
	public void set(BlockCell<T> src) {
		set(src.point(), src.get());
	}

	/**
	 * 指定されたブロックセルオペレーターから値を取得して、同じ座標に設定します。
	 *
	 * @param src 設定する値が格納されたブロックセルオペレーター
	 */
	public void set(BlockCellOperator<T> src) {
		src.cells().forEach(this::set);
	}

	/**
	 * ブロックパッチャーを使用して値を変更します。
	 *
	 * @param patcher ブロックパッチャー
	 */
	public void patch(BlockPatcher<T> patcher) {
		patcher.patch(this);
	}

	/**
	 * ブロック列を取得します。
	 *
	 * @param x 横座標
	 * @return ブロック列
	 */
	public BlockCol<T> col(int x) {
		return new BlockCol<>(this, x);
	}

	/**
	 * ブロック列を取得します。
	 *
	 * @return ブロック列のストリーム (横座標の昇順)
	 */
	public Stream<BlockCol<T>> cols() {
		return rectangle.width().stream().map(this::col);
	}

	/**
	 * ブロック行を取得します。
	 *
	 * @param y 縦座標
	 * @return ブロック行
	 */
	public BlockRow<T> row(int y) {
		return new BlockRow<>(this, y);
	}

	/**
	 * ブロック行を取得します。
	 *
	 * @return ブロック行のストリーム (縦座標の昇順)
	 */
	public Stream<BlockRow<T>> rows() {
		return rectangle.height().stream().map(this::row);
	}

	/**
	 * ブロックセルを取得します。
	 *
	 * @param x 横座標
	 * @param y 縦座標
	 * @return ブロックセル
	 */
	public BlockCell<T> cell(int x, int y) {
		return new BlockCell<>(this, rectangle.point(x, y));
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
		return rectangle.stream().map(point -> new BlockCell<T>(this, point));
	}

	/**
	 * このブロック配列を複製します。
	 *
	 * @return 複製されたブロック配列
	 */
	@Override
	public BlockArray<T> clone() {
		T[] copied = Arrays.copyOf(array, array.length);
		return new BlockArray<>(rectangle, copied);
	}
}
