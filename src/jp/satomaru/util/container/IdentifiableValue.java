package jp.satomaru.util.container;

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
	 * 説明文を作成します。
	 *
	 * @return 説明文
	 */
	default String description() {
		String type = (value() == null) ? "?" : value().getClass().getSimpleName();
		return String.format("{<%s> %s: %s}", type, id(), value());
	}
}
