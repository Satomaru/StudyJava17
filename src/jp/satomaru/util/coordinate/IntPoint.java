package jp.satomaru.util.coordinate;

/**
 * 2次元の整数座標です。
 *
 * @param x 横座標
 * @param y 縦座標
 * @see IntRectangle
 * @author Satomaru
 */
public record IntPoint(int x, int y) {

	/** 横座標・縦座標を受け取るコンシューマです。 */
	@FunctionalInterface
	public interface ExtractedConsumer {

		void accept(int x, int y);

		default void accept(IntPoint point) {
			accept(point.x, point.y);
		}
	}

	/** 横座標・縦座標を受け取るファンクションです。 */
	@FunctionalInterface
	public interface ExtractedFunction<R> {

		R apply(int x, int y);

		default R apply(IntPoint point) {
			return apply(point.x, point.y);
		}
	}

	/**
	 * このポイントから移動したポイントを作成します。
	 *
	 * @param offsetX 横差分
	 * @param offsetY 縦差分
	 * @return 移動したポイント
	 */
	public IntPoint move(int offsetX, int offsetY) {
		return new IntPoint(x + offsetX, y + offsetY);
	}

	/**
	 * 上に1移動したポイントを作成します。
	 *
	 * @return 上に1移動したポイント
	 */
	public IntPoint up() {
		return move(0, -1);
	}

	/**
	 * 下に1移動したポイントを作成します。
	 *
	 * @return 下に1移動したポイント
	 */
	public IntPoint down() {
		return move(0, 1);
	}

	/**
	 * 左に1移動したポイントを作成します。
	 *
	 * @return 左に1移動したポイント
	 */
	public IntPoint left() {
		return move(-1, 0);
	}

	/**
	 * 右に1移動したポイントを作成します。
	 *
	 * @return 右に1移動したポイント
	 */
	public IntPoint right() {
		return move(1, 0);
	}

	/**
	 * 横座標・縦座標を受け取るコンシューマを実行します。
	 *
	 * @param action 横座標・縦座標を受け取るコンシューマ
	 */
	public void accept(ExtractedConsumer action) {
		action.accept(this);
	}

	/**
	 * 横座標・縦座標を受け取るファンクションを実行します。
	 *
	 * @param <R> 処理結果
	 * @param action 横座標・縦座標を受け取るファンクション
	 * @return 処理結果
	 */
	public <R> R apply(ExtractedFunction<R> action) {
		return action.apply(this);
	}
}
