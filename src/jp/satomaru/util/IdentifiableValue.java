package jp.satomaru.util;

/**
 * 値に識別子を持たせます。
 *
 * @author Satomaru
 *
 * @param <I> 識別子
 * @param <V> 値
 */
public interface IdentifiableValue<I, V> extends Identifiable<I> {

	/**
	 * 値。
	 */
	V value();

	/**
	 * 値の型名を取得します。
	 *
	 * @return 値の型名
	 */
	default String getValueTypeName() {
		if (value() == null) {
			return "?";
		}

		return value().getClass().getSimpleName();
	}

	/**
	 * 説明文を作成します。
	 *
	 * @return 説明文
	 */
	default String getValueDescription() {
		return String.format("{<%s> %s: %s}", getValueTypeName(), id(), value());
	}
}
