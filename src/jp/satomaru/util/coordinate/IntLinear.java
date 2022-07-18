package jp.satomaru.util.coordinate;

import java.util.NoSuchElementException;

import jp.satomaru.util.SequentialIdSet;

/**
 * 1次元線形の整数座標セットです。
 *
 * @param begin 起点 (最小値)
 * @param end 終点 (最大値)
 * @author Satomaru
 */
public record IntLinear(int begin, int end)
	implements SequentialIdSet<Integer, IntLinear> {

	/**
	 * リニアを作成します。
	 *
	 * <p>
	 * 長さが1未満の場合は {@link IllegalArgumentException} がスローされます。
	 *
	 * @param length 長さ (0から長さ-1までのリニアとなる)
	 * @return
	 */
	public static IntLinear of(int length) {
		if (length < 1) {
			throw new IllegalArgumentException("length < 1");
		}

		return new IntLinear(0, length - 1);
	}

	/**
	 * リニアを作成します。
	 *
	 * <p>
	 * 起点が終点よりも大きい場合は {@link IllegalArgumentException} がスローされます。
	 *
	 * @param begin 起点 (最小値)
	 * @param end 終点 (最大値)
	 */
	public IntLinear {
		if (begin > end) {
			throw new IllegalArgumentException("begin > end");
		}
	}

	/**
	 * 一部分を切り取ります。
	 *
	 * <p>
	 * 指定された起点および終点の座標が存在しない場合は
	 * {@link NoSuchElementException} をスローします。
	 *
	 * @param begin 起点 (最小値)
	 * @param end 終点 (最大値)
	 * @return
	 */
	public IntLinear part(Integer begin, Integer end) {
		return new IntLinear(validate(begin), validate(end));
	}

	/**
	 * 座標の総数の取得します。
	 *
	 * @return 座標の総数
	 */
	@Override
	public int size() {
		return end - begin + 1;
	}

	/**
	 * 座標のインデックスを数えます。
	 *
	 * <p>
	 * インデックスは必ず、0以上 {@link #size()} 未満となります。
	 * なお、指定された座標が存在しない場合は、
	 * {@link NoSuchElementException} がスローされます。
	 *
	 * @param coordinate 座標
	 * @return インデックス
	 */
	@Override
	public int seek(Integer coordinate) {
		if (coordinate < begin || coordinate > end) {
			throw new NoSuchElementException();
		}

		return coordinate - begin;
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
	public Integer pick(int index) {
		if (index < 0 || index >= size()) {
			throw new NoSuchElementException();
		}

		return begin + index;
	}

	/**
	 * 指定された座標が含まれていることを判定します。
	 *
	 * @param coordinate 座標
	 * @return 座標が含まれている場合はtrue
	 */
	@Override
	public boolean contains(Integer coordinate) {
		return coordinate >= begin && coordinate <= end;
	}

	/**
	 * 指定されたリニアの座標が全て含まれていることを判定します。
	 *
	 * @param linear リニア
	 * @return 座標が全て含まれている場合はtrue
	 */
	@Override
	public boolean contains(IntLinear linear) {
		return contains(linear.begin) && contains(linear.end);
	}
}
