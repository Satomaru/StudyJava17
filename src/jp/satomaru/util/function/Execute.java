package jp.satomaru.util.function;

/**
 * ラムダ式やメソッド参照を、RetArg*またはVoidArg*にキャストします。
 *
 * @author Satomaru
 */
public final class Execute {

	/**
	 * 引数なし戻り値ありの関数を、RetArg0にキャストします。
	 *
	 * @param <R>     戻り値
	 * @param retArg0 引数なし戻り値ありの関数
	 * @return RetArg0
	 */
	public static <R> RetArg0<R> of(RetArg0<R> retArg0) {
		return retArg0;
	}

	/**
	 * 引数ひとつ戻り値ありの関数を、RetArg1にキャストします。
	 *
	 * @param <A1>    引数1
	 * @param <R>     戻り値
	 * @param retArg1 引数ひとつ戻り値ありの関数
	 * @return RetArg1
	 */
	public static <A1, R> RetArg1<A1, R> of(RetArg1<A1, R> retArg1) {
		return retArg1;
	}

	/**
	 * 引数ふたつ戻り値ありの関数を、RetArg2にキャストします。
	 *
	 * @param <A1>    引数1
	 * @param <A2>    引数2
	 * @param <R>     戻り値
	 * @param retArg2 引数ふたつ戻り値ありの関数
	 * @return RetArg2
	 */
	public static <A1, A2, R> RetArg2<A1, A2, R> of(RetArg2<A1, A2, R> retArg2) {
		return retArg2;
	}

	/**
	 * 引数みっつ戻り値ありの関数を、RetArg3にキャストします。
	 *
	 * @param <A1>    引数1
	 * @param <A2>    引数2
	 * @param <A3>    引数3
	 * @param <R>     戻り値
	 * @param retArg3 引数みっつ戻り値ありの関数
	 * @return RetArg3
	 */
	public static <A1, A2, A3, R> RetArg3<A1, A2, A3, R> of(RetArg3<A1, A2, A3, R> retArg3) {
		return retArg3;
	}

	/**
	 * 引数なし戻り値なしの関数を、VoidArg0にキャストします。
	 *
	 * @param voidArg0 引数なし戻り値なしの関数
	 * @return VoidArg0
	 */
	public static VoidArg0 of(VoidArg0 voidArg0) {
		return voidArg0;
	}

	/**
	 * 引数ひとつ戻り値なしの関数を、VoidArg1にキャストします。
	 *
	 * @param <A1>     引数1
	 * @param voidArg1 引数ひとつ戻り値なしの関数
	 * @return VoidArg1
	 */
	public static <A1> VoidArg1<A1> of(VoidArg1<A1> voidArg1) {
		return voidArg1;
	}

	/**
	 * 引数ふたつ戻り値なしの関数を、VoidArg2にキャストします。
	 *
	 * @param <A1>     引数1
	 * @param <A2>     引数2
	 * @param voidArg2 引数ふたつ戻り値なしの関数
	 * @return VoidArg2
	 */
	public static <A1, A2> VoidArg2<A1, A2> of(VoidArg2<A1, A2> voidArg2) {
		return voidArg2;
	}

	/**
	 * 引数みっつ戻り値なしの関数を、VoidArg2にキャストします。
	 *
	 * @param <A1>     引数1
	 * @param <A2>     引数2
	 * @param <A3>     引数3
	 * @param voidArg3 引数みっつ戻り値なしの関数
	 * @return VoidArg3
	 */
	public static <A1, A2, A3> VoidArg3<A1, A2, A3> of(VoidArg3<A1, A2, A3> voidArg3) {
		return voidArg3;
	}

	private Execute() {
	}
}
