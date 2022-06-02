package jp.satomaru.util.either;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * ふたつの型の内、どちらかを保持します。
 *
 * @author Satomaru
 * @param <L> 左の型
 * @param <R> 右の型
 */
public sealed interface Either<L, R> permits EitherRight<L, R>, EitherLeft<L, R> {

	/**
	 * 保持している値を取得します。
	 *
	 * @return 保持している値
	 */
	Object value();

	/**
	 * 右の型を保持していることを判定します。
	 *
	 * @return 右の型を保持している場合はtrue
	 */
	boolean isRight();

	/**
	 * 左の型を保持していることを判定します。
	 *
	 * @return 左の型を保持している場合はtrue
	 */
	default boolean isLeft() {
		return !isRight();
	}

	/**
	 * 保持している値を変換します。
	 *
	 * @param <T>       変換後の型
	 * @param whenLeft  左の型を保持している場合に使用する関数
	 * @param whenRight 右の型を保持している場合に使用する関数
	 * @return 変換後の値
	 */
	default <T> T map(Function<L, T> whenLeft, Function<R, T> whenRight) {
		if (isRight()) {
			return whenRight.apply(rightValue());
		} else {
			return whenLeft.apply(leftValue());
		}
	}

	/**
	 * 右の値を保持している場合、指定された関数を実行します。
	 *
	 * @param whenRight 右の型を保持している場合に使用する関数
	 * @return このオブジェクト自身
	 */
	default Either<L, R> ifRight(Consumer<R> whenRight) {
		if (isRight()) {
			whenRight.accept(rightValue());
		}

		return this;
	}

	/**
	 * 左の値を保持している場合、指定された関数を実行します。
	 *
	 * @param whenLeft 左の型を保持している場合に使用する関数
	 * @return このオブジェクト自身
	 */
	default Either<L, R> ifLeft(Consumer<L> whenLeft) {
		if (isLeft()) {
			whenLeft.accept(leftValue());
		}

		return this;
	}

	private R rightValue() {
		if (!isRight()) {
			throw new IllegalStateException();
		}

		@SuppressWarnings("unchecked")
		var right = (R) value();

		return right;
	}

	private L leftValue() {
		if (!isLeft()) {
			throw new IllegalStateException();
		}

		@SuppressWarnings("unchecked")
		var left = (L) value();

		return left;
	}
}
