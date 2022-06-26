package jp.satomaru.util.container;

import java.util.function.Predicate;

import jp.satomaru.util.Tester;
import jp.satomaru.util.function.TwoDimConsumer;
import jp.satomaru.util.function.TwoDimSupplier;

/**
 * マトリックスのセル。
 *
 * @author Satomaru
 *
 * @param <T> セルが持つ値
 */
public class MatrixCell<T> {

	/** コンテキスト。 */
	private final MatrixContext<T> context;

	/** 列番号。 */
	private final int x;

	/** 行番号。 */
	private final int y;

	MatrixCell(MatrixContext<T> context, int x, int y) {
		this.context = context;
		this.x = context.testX(x);
		this.y = context.textY(y);
	}

	/**
	 * 列番号を取得します。
	 *
	 * @return 列番号
	 */
	public int x() {
		return x;
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
	 * 値を取得します。
	 *
	 * @return 値
	 */
	public T get() {
		return context.get(x, y);
	}

	/**
	 * 値を取得します。
	 *
	 * @param consumer 列番号、行番号、値を受け取る関数
	 */
	public void get(TwoDimConsumer<T> consumer) {
		consumer.accept(x, y, get());
	}

	/**
	 * 値を設定します。
	 *
	 * @param value 値
	 */
	public void set(T value) {
		context.set(x, y, value);
	}

	/**
	 * 値を設定します。
	 *
	 * @param supplier 列番号と行番号から値を作成する関数
	 */
	public void set(TwoDimSupplier<T> supplier) {
		set(supplier.get(x, y));
	}

	/**
	 * 値が、期待する値であることを判定します。
	 *
	 * @param value 期待する値
	 * @return 期待する値である場合はtrue
	 */
	public boolean eq(T value) {
		return is(Tester.eq(value));
	}

	/**
	 * 値が、期待しない値ではないことを判定します。
	 *
	 * @param value 期待しない値
	 * @return 期待しない値ではない場合はtrue
	 */
	public boolean not(T value) {
		return is(Tester.not(value));
	}

	/**
	 * 値を判定します。
	 *
	 * @param predicate 判定する関数
	 * @return 判定結果
	 */
	public boolean is(Predicate<T> predicate) {
		return predicate.test(get());
	}

	/**
	 * 値を交換します。
	 *
	 * @param other 交換先のセル
	 */
	public void swap(MatrixCell<T> other) {
		T otherValue = other.get();
		other.set(get());
		set(otherValue);
	}
}
