package jp.satomaru.util;

/**
 * 識別子を持たせます。
 *
 * @author Satomaru
 *
 * @param <I> 識別子
 */
public interface Identifiable<I> {

	/**
	 * 識別子。
	 */
	I id();
}
