package jp.satomaru.util.container;

import java.util.stream.Stream;

import jp.satomaru.util.Tester;
import jp.satomaru.util.function.TwoDimConsumer;
import jp.satomaru.util.function.TwoDimSupplier;

/**
 * マトリックスのセルを操作します。
 *
 * @author Satomaru
 *
 * @param <T> セルが持つ値
 */
public sealed interface MatrixCellOperator<T> permits Matrix<T>, MatrixCol<T>, MatrixRow<T> {

	/**
	 * 全てのセルを取得します。
	 *
	 * @return セル
	 */
	Stream<MatrixCell<T>> cells();

	/**
	 * 指定された値を持つセルを検索します。
	 *
	 * @param value 検索する値
	 * @return セル
	 */
	default Stream<MatrixCell<T>> find(T value) {
		return cells().filter(cell -> cell.eq(value));
	}

	/**
	 * 指定された値を持つセルが存在することを判定します。
	 *
	 * @param value 値
	 * @return 指定された値を持つセルが存在する場合はtrue
	 */
	default boolean contains(T value) {
		return cells().map(MatrixCell::get).anyMatch(Tester.eq(value));
	}

	/**
	 * 全てのセルの値を取得します。
	 *
	 * @param consumer 列番号、行番号、値を受け取る関数
	 */
	default void forEach(TwoDimConsumer<T> consumer) {
		cells().forEach(cell -> cell.get(consumer));
	}

	/**
	 * 全てのセルの値を設定します。
	 *
	 * @param supplier 列番号と行番号を受け取って値を作成する関数
	 */
	default void fill(TwoDimSupplier<T> supplier) {
		cells().forEach(cell -> cell.set(supplier));
	}

	/**
	 * 全てのセルの値を設定します。
	 *
	 * @param value 値
	 */
	default void fill(T value) {
		cells().forEach(cell -> cell.set(value));
	}
}
