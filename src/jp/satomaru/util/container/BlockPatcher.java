package jp.satomaru.util.container;

import java.util.function.Supplier;

import jp.satomaru.util.Strings;
import jp.satomaru.util.coordinate.IntPoint;

/**
 * ブロック配列の値を変更します。
 *
 * @author Satomaru
 * @param <T> 内部配列の値
 */
public interface BlockPatcher<T> {

	/**
	 * ブロックセルの値を変更するブロックパッチャーを作成します。
	 *
	 * @param <T> 内部配列の値
	 * @param x ブロックセルの横座標
	 * @param y ブロックセルの縦座標
	 * @param src 値を取得する関数
	 * @return ブロックパッチャー
	 */
	public static <T> BlockPatcher<T> cell(int x, int y, Supplier<T> src) {
		IntPoint point = new IntPoint(x, y);
		return blockArray -> blockArray.set(point, src.get());
	}

	/**
	 * 指定された座標から右に向かって値を変更するブロックパッチャーを作成します。
	 *
	 * @param <T> 内部配列の値
	 * @param left 起点となるブロックセルの横座標
	 * @param y ブロックセルの縦座標
	 * @param src 値を取得する関数
	 * @return ブロックパッチャー
	 */
	public static <T> BlockPatcher<T> line(int left, int y, Supplier<T[]> src) {
		IntPoint point = new IntPoint(left, y);
		return blockArray -> blockArray.set(point, src.get());
	}

	/**
	 * 指定された座標から右下に向かって値を変更するブロックパッチャーを作成します。
	 *
	 * @param <T> 内部配列の値
	 * @param left 起点となるブロックセルの横座標
	 * @param top 起点となるブロックセルの縦座標
	 * @param src 値を取得する関数
	 * @return ブロックパッチャー
	 */
	public static <T> BlockPatcher<T> block(int left, int top, Supplier<T[][]> src) {
		IntPoint point = new IntPoint(left, top);
		return blockArray -> blockArray.set(point, src.get());
	}

	/**
	 * ブロックセルから値を取得して変更するブロックパッチャーを作成します。
	 *
	 * @param <T> 内部配列の値
	 * @param src ブロックセル
	 * @return ブロックパッチャー
	 */
	public static <T> BlockPatcher<T> cell(BlockCell<T> src) {
		return blockArray -> blockArray.set(src);
	}

	/**
	 * ブロックオペレーターから値を取得して変更するブロックパッチャーを作成します。
	 *
	 * @param <T> 内部配列の値
	 * @param src ブロックオペレーター
	 * @return ブロックパッチャー
	 */
	public static <T> BlockPatcher<T> cells(BlockCellOperator<T> src) {
		return blockArray -> blockArray.set(src);
	}

	/**
	 * 指定された座標から右下に向かって値を変更する文字ブロックパッチャーを作成します。
	 *
	 * <p>
	 * このブロックパッチャーは、関数から値を取得した後、
	 * 文字列に変換し、さらに改行で分割して、Characterの2次元配列を作成します。
	 * そして、作成したCharacterの2次元配列で、文字ブロック配列を変更します。
	 *
	 * @param left 起点となる文字ブロックセルの横座標
	 * @param top 起点となる文字ブロックセルの縦座標
	 * @param src 値を取得する関数
	 * @return 文字ブロックパッチャー
	 */
	public static BlockPatcher<Character> chars(int left, int top, Supplier<?> src) {
		Supplier<Character[][]> charBlock = () -> Strings.charBlock(String.valueOf(src.get()));
		return block(left, top, charBlock);
	}

	/**
	 * 指定された座標から右下に向かって値を変更する文字ブロックパッチャーを作成します。
	 *
	 * <p>
	 * このブロックパッチャーは、配列から値を取得した後、
	 * 文字列に変換し、さらに改行で分割して、Characterの2次元配列を作成します。
	 * そして、作成したCharacterの2次元配列で、文字ブロック配列を変更します。
	 *
	 * @param left 起点となる文字ブロックセルの横座標
	 * @param top 起点となる文字ブロックセルの縦座標
	 * @param format 文字列に変換する際のフォーマット ({@link java.util.Formatter} と同じ)
	 * @param src 値を取得する配列
	 * @param index 値を取得するインデックス
	 * @return 文字ブロックパッチャー
	 */
	public static BlockPatcher<Character> chars(int left, int top, String format, Object[] src, int index) {
		Supplier<Character[][]> charBlock = () -> Strings.charBlock(String.format(format, src[index]));
		return block(left, top, charBlock);
	}

	/**
	 * ブロック配列の値を変更します。
	 *
	 * @param blockArray ブロック配列
	 */
	void patch(BlockArray<T> blockArray);
}
