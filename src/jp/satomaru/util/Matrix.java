package jp.satomaru.util;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class Matrix<T> {

	@FunctionalInterface
	public interface CellGenerator<T> {
		T generate(int x, int y);
	}

	@FunctionalInterface
	public interface CellGeneratorOfLine<T> {
		T generate(int index);
	}

	@FunctionalInterface
	public interface CellAcceptor<T> {
		void accept(int x, int y, T value);
	}

	@FunctionalInterface
	public interface CellAcceptorOfLine<T> {
		void accept(int index, T value);
	}

	@FunctionalInterface
	public interface RowAcceptor<T> {
		void accept(int y, Matrix<T>.Row row);
	}

	@FunctionalInterface
	public interface ColAcceptor<T> {
		void accept(int x, Matrix<T>.Col col);
	}

	public abstract sealed class Line permits Row, Col {

		private final int count;

		private Line(int count) {
			this.count = count;
		}

		public abstract T get(int index);

		public abstract void set(int index, T value);

		public final boolean is(int index, T value) {
			return get(index).equals(value);
		}

		public final void fill(CellGeneratorOfLine<T> generator) {
			for (int index = 0; index < count; index++) {
				set(index, generator.generate(index));
			}
		}

		public final void forEach(CellAcceptorOfLine<T> acceptor) {
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

	public T get(int x, int y) {
		return cells[x + y * width];
	}

	public void set(int x, int y, T value) {
		cells[x + y * width] = value;
	}

	public boolean is(int x, int y, T value) {
		return get(x, y).equals(value);
	}

	public void fill(CellGenerator<T> generator) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				set(x, y, generator.generate(x, y));
			}
		}
	}

	public void forEach(CellAcceptor<T> acceptor) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				acceptor.accept(x, y, get(x, y));
			}
		}
	}

	public void forEachRow(RowAcceptor<T> acceptor) {
		for (int y = 0; y < height; y++) {
			acceptor.accept(y, row(y));
		}
	}

	public void forEachCol(ColAcceptor<T> acceptor) {
		for (int x = 0; x < width; x++) {
			acceptor.accept(x, col(x));
		}
	}

	public Stream<T> stream() {
		return Arrays.stream(cells);
	}
}
