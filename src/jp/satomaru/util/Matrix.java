package jp.satomaru.util;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import jp.satomaru.util.function.IndexedConsumer;
import jp.satomaru.util.function.TwoDimConsumer;
import jp.satomaru.util.function.TwoDimSupplier;

public final class Matrix<T> {

	public abstract sealed class Line permits Row, Col {

		private final int count;

		private Line(int count) {
			this.count = count;
		}

		public abstract T get(int index);

		public abstract void set(int index, T value);

		public abstract Cell cell(int index);

		public final boolean is(int index, T value) {
			return get(index).equals(value);
		}

		public final void fill(IntFunction<T> generator) {
			for (int index = 0; index < count; index++) {
				set(index, generator.apply(index));
			}
		}

		public final void forEach(IndexedConsumer<T> acceptor) {
			for (int index = 0; index < count; index++) {
				acceptor.accept(index, get(index));
			}
		}

		public final Stream<T> stream() {
			return IntStream.range(0, count).mapToObj(this::get);
		}
	}

	public final class Row extends Line {

		private final int y;

		private Row(int y) {
			super(width);
			this.y = y;
		}

		@Override
		public T get(int index) {
			return Matrix.this.get(index, y);
		}

		@Override
		public void set(int index, T value) {
			Matrix.this.set(index, y, value);
		}

		@Override
		public Cell cell(int index) {
			return new Cell(index, y);
		}
	}

	public final class Col extends Line {

		private final int x;

		private Col(int x) {
			super(height);
			this.x = x;
		}

		@Override
		public T get(int index) {
			return Matrix.this.get(x, index);
		}

		@Override
		public void set(int index, T value) {
			Matrix.this.set(x, index, value);
		}

		@Override
		public Cell cell(int index) {
			return new Cell(x, index);
		}
	}

	public final class Cell {

		private final int x;

		private final int y;

		private Cell(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public void swap(int otherX, int otherY) {
			T otherValue = get(otherX, otherY);
			set(otherX, otherY, get(x, y));
			set(x, y, otherValue);
		}
	}

	private final int width;

	private final int height;

	private final T[] cells;

	public Matrix(int width, int height, IntFunction<T[]> constructor) {
		this.width = width;
		this.height = height;
		this.cells = constructor.apply(width * height);
	}

	public int width() {
		return width;
	}

	public int height() {
		return height;
	}

	public Row row(int y) {
		return new Row(y);
	}

	public Col col(int x) {
		return new Col(x);
	}

	public Cell cell(int x, int y) {
		return new Cell(x, y);
	}

	public T get(int x, int y) {
		return cells[x + y * width];
	}

	public void set(int x, int y, T value) {
		cells[x + y * width] = value;
	}

	public Optional<Cell> findAny(T value) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (is(x, y, value)) {
					return Optional.of(cell(x, y));
				}
			}
		}

		return Optional.empty();
	}

	public boolean is(int x, int y, T value) {
		return get(x, y).equals(value);
	}

	public void fill(TwoDimSupplier<T> generator) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				set(x, y, generator.get(x, y));
			}
		}
	}

	public void forEach(TwoDimConsumer<T> acceptor) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				acceptor.accept(x, y, get(x, y));
			}
		}
	}

	public void forEachRow(IndexedConsumer<Row> acceptor) {
		for (int y = 0; y < height; y++) {
			acceptor.accept(y, row(y));
		}
	}

	public void forEachCol(IndexedConsumer<Col> acceptor) {
		for (int x = 0; x < width; x++) {
			acceptor.accept(x, col(x));
		}
	}

	public Stream<T> stream() {
		return Arrays.stream(cells);
	}
}
