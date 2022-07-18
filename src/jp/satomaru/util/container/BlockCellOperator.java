package jp.satomaru.util.container;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import jp.satomaru.util.coordinate.IntRectangle;
import jp.satomaru.util.io.LineAppender;

/**
 * ブロックセルを操作します。
 *
 * @author Satomaru
 * @param <T> 内部配列の値
 */
public interface BlockCellOperator<T> {

	/**
	 * 座標セットを取得します。
	 *
	 * @return 座標セット
	 */
	IntRectangle rectangle();

	/**
	 * ブロックセルのストリームを作成します。
	 *
	 * @return ブロックセルのストリーム
	 */
	Stream<BlockCell<T>> cells();

	/**
	 * 全てのブロックセルに対して関数を実行します。
	 *
	 * @param action 関数
	 */
	default void forEach(BlockCell.ExtractedConsumer<T> action) {
		cells().forEach(cell -> cell.get(action));
	}

	/**
	 * 全てのブロックセルに対して関数を実行し、結果を値に設定します。
	 *
	 * @param action 関数
	 */
	default void fill(BlockCell.ExtractedOperator<T> action) {
		cells().forEach(cell -> cell.set(action));
	}

	/**
	 * 全てのブロックセルに値を設定します。
	 *
	 * @param action 値を取得する関数
	 */
	default void fill(Supplier<T> action) {
		cells().forEach(cell -> cell.set(action.get()));
	}

	/**
	 * 全てのブロックセルに値を設定します。
	 *
	 * @param value 値
	 */
	default void fill(T value) {
		cells().forEach(cell -> cell.set(value));
	}

	/**
	 * 指定された値のブロックセルを数えます。
	 *
	 * @param value 値
	 * @return ブロックセル
	 */
	default long count(T value) {
		return cells().filter(cell -> value.equals(cell.get())).count();
	}

	/**
	 * 指定された値のブロックセルを検索します。
	 *
	 * @param value 値
	 * @return ブロックセル
	 */
	default Optional<BlockCell<T>> findAny(T value) {
		return cells().filter(cell -> value.equals(cell.get())).findAny();
	}

	/**
	 * 指定された値のブロックセルが存在することを判定します。
	 *
	 * @param value 値
	 * @return 指定された値のブロックセルが存在する場合はtrue
	 */
	default boolean anyMatch(T value) {
		return cells().anyMatch(cell -> value.equals(cell.get()));
	}

	/**
	 * ラインアペンダーに全ての値を追加します。
	 *
	 * @param appender ラインアペンダー
	 */
	default void appendTo(LineAppender<?> appender) {
		int right = rectangle().width().end();

		forEach((x, y, value) -> {
			appender.write(value);

			if (x == right) {
				appender.newLine();
			}
		});
	}
}
