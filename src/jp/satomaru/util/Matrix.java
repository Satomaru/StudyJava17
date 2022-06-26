package jp.satomaru.util;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import jp.satomaru.util.function.TwoDimConsumer;
import jp.satomaru.util.function.TwoDimSupplier;

/**
 * 二次元配列。
 *
 * @author Satomaru
 *
 * @param <T> 格納されている値
 */
public final class Matrix<T> {

	/** セル。 */
	public final class Cell {

		/** 列番号。 */
		private final int x;

		/** 行番号。 */
		private final int y;

		private Cell(int x, int y) {
			this.x = Tester.must("x", x, Tester.range(0, width));
			this.y = Tester.must("y", y, Tester.range(0, height));
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
			return values[x + y * width];
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
			values[x + y * width] = value;
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
		 * 値が期待する値であるを判定します。
		 *
		 * @param value 期待する値
		 * @return 値が期待する値である場合はtrue
		 */
		public boolean eq(T value) {
			return is(Tester.eq(value));
		}

		/**
		 * 値が期待しない値ではないことを判定します。
		 *
		 * @param value 期待しない値
		 * @return 値が期待しない値ではない場合はtrue
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
		public void swap(Cell other) {
			T otherValue = other.get();
			other.set(get());
			set(otherValue);
		}
	}

	/** 列。 */
	public final class Col {

		/** 列番号。 */
		private final int x;

		private Col(int x) {
			this.x = Tester.must("x", x, Tester.range(0, width));
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
		 * セルを取得します。
		 *
		 * @return セル
		 */
		public Stream<Cell> cells() {
			return IntStream.range(0, height).mapToObj(y -> new Cell(x, y));
		}

		/**
		 * セルを取得します。
		 *
		 * @param y 行番号
		 * @return セル
		 */
		public Cell cell(int y) {
			return new Cell(x, y);
		}

		/**
		 * 指定された値を持つセルを検索します。
		 *
		 * @param value 検索する値
		 * @return セル
		 */
		public Stream<Cell> find(T value) {
			return cells().filter(cell -> cell.eq(value));
		}

		/**
		 * 指定された値を含むことを判定します。
		 *
		 * @param value 値
		 * @return 指定された値を含む場合はtrue
		 */
		public boolean contains(T value) {
			return cells().map(Cell::get).anyMatch(value::equals);
		}

		/**
		 * 全てのセルの値を取得します。
		 *
		 * @param consumer 列番号、行番号、値を受け取る関数
		 */
		public void forEach(TwoDimConsumer<T> consumer) {
			cells().forEach(cell -> cell.get(consumer));
		}

		/**
		 * 全てのセルの値を設定します。
		 *
		 * @param supplier 列番号と行番号を受け取って値を作成する関数
		 */
		public void fill(TwoDimSupplier<T> supplier) {
			cells().forEach(cell -> cell.set(supplier));
		}
	}

	/** 行。 */
	public final class Row {

		/** 行番号。 */
		private final int y;

		private Row(int y) {
			this.y = Tester.must("y", y, Tester.range(0, height));
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
		 * セルを取得します。
		 *
		 * @return セル
		 */
		public Stream<Cell> cells() {
			return IntStream.range(0, width).mapToObj(x -> new Cell(x, y));
		}

		/**
		 * セルを取得します。
		 *
		 * @param x 列番号
		 * @return セル
		 */
		public Cell cell(int x) {
			return new Cell(x, y);
		}

		/**
		 * 指定された値を持つセルを検索します。
		 *
		 * @param value 検索する値
		 * @return セル
		 */
		public Stream<Cell> find(T value) {
			return cells().filter(cell -> cell.eq(value));
		}

		/**
		 * 指定された値を含むことを判定します。
		 *
		 * @param value 値
		 * @return 指定された値を含む場合はtrue
		 */
		public boolean contains(T value) {
			return cells().map(Cell::get).anyMatch(value::equals);
		}

		/**
		 * 全てのセルの値を取得します。
		 *
		 * @param consumer 列番号、行番号、値を受け取る関数
		 */
		public void forEach(TwoDimConsumer<T> consumer) {
			cells().forEach(cell -> cell.get(consumer));
		}

		/**
		 * 全てのセルの値を設定します。
		 *
		 * @param supplier 列番号と行番号を受け取って値を作成する関数
		 */
		public void fill(TwoDimSupplier<T> supplier) {
			cells().forEach(cell -> cell.set(supplier));
		}
	}

	/** 列数。 */
	private final int width;

	/** 行数。 */
	private final int height;

	/** 値。 */
	private final T[] values;

	/**
	 * 二次元配列を作成します。
	 *
	 * @param width       列数
	 * @param height      行数
	 * @param constructor 一次元配列のコンストラクタ
	 */
	public Matrix(int width, int height, IntFunction<T[]> constructor) {
		this.width = Tester.must("width", width, Tester.gt(0));
		this.height = Tester.must("height", height, Tester.gt(0));
		this.values = constructor.apply(width * height);
	}

	/**
	 * 列数を取得します。
	 *
	 * @return 列数
	 */
	public int width() {
		return width;
	}

	/**
	 * 行数を取得します。
	 *
	 * @return 行数
	 */
	public int height() {
		return height;
	}

	/**
	 * 行を取得します。
	 *
	 * @return 行
	 */
	public Stream<Row> rows() {
		return IntStream.range(0, height).mapToObj(y -> new Row(y));
	}

	/**
	 * 行を取得します。
	 *
	 * @param y 行番号
	 * @return 行
	 */
	public Row row(int y) {
		return new Row(y);
	}

	/**
	 * 列を取得します。
	 *
	 * @return 列
	 */
	public Stream<Col> cols() {
		return IntStream.range(0, width).mapToObj(x -> new Col(x));
	}

	/**
	 * 列を取得します。
	 *
	 * @param x 列番号
	 * @return 列
	 */
	public Col col(int x) {
		return new Col(x);
	}

	/**
	 * セルを取得します。
	 *
	 * @return セル
	 */
	public Stream<Cell> cells() {
		return rows().flatMap(Row::cells);
	}

	/**
	 * セルを取得します。
	 *
	 * @param x 列番号
	 * @param y 行番号
	 * @return セル
	 */
	public Cell cell(int x, int y) {
		return new Cell(x, y);
	}

	/**
	 * 指定された値を持つセルを検索します。
	 *
	 * @param value 検索する値
	 * @return セル
	 */
	public Stream<Cell> find(T value) {
		return cells().filter(cell -> cell.eq(value));
	}

	/**
	 * 指定された値を含むことを判定します。
	 *
	 * @param value 値
	 * @return 指定された値を含む場合はtrue
	 */
	public boolean contains(T value) {
		return Arrays.stream(values).anyMatch(value::equals);
	}

	/**
	 * 全てのセルの値を取得します。
	 *
	 * @param consumer 列番号、行番号、値を受け取る関数
	 */
	public void forEach(TwoDimConsumer<T> consumer) {
		cells().forEach(cell -> cell.get(consumer));
	}

	/**
	 * 全てのセルの値を設定します。
	 *
	 * @param supplier 列番号と行番号を受け取って値を作成する関数
	 */
	public void fill(TwoDimSupplier<T> supplier) {
		cells().forEach(cell -> cell.set(supplier));
	}
}
