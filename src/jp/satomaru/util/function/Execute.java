package jp.satomaru.util.function;

/**
 * �����_���⃁�\�b�h�Q�Ƃ��ARetArg*�܂���VoidArg*�ɃL���X�g���܂��B
 *
 * @author Satomaru
 */
public final class Execute {

	/**
	 * �����Ȃ��߂�l����̊֐����ARetArg0�ɃL���X�g���܂��B
	 *
	 * @param <R>     �߂�l
	 * @param retArg0 �����Ȃ��߂�l����̊֐�
	 * @return RetArg0
	 */
	public static <R> RetArg0<R> of(RetArg0<R> retArg0) {
		return retArg0;
	}

	/**
	 * �����ЂƂ߂�l����̊֐����ARetArg1�ɃL���X�g���܂��B
	 *
	 * @param <A1>    ����1
	 * @param <R>     �߂�l
	 * @param retArg1 �����ЂƂ߂�l����̊֐�
	 * @return RetArg1
	 */
	public static <A1, R> RetArg1<A1, R> of(RetArg1<A1, R> retArg1) {
		return retArg1;
	}

	/**
	 * �����ӂ��߂�l����̊֐����ARetArg2�ɃL���X�g���܂��B
	 *
	 * @param <A1>    ����1
	 * @param <A2>    ����2
	 * @param <R>     �߂�l
	 * @param retArg2 �����ӂ��߂�l����̊֐�
	 * @return RetArg2
	 */
	public static <A1, A2, R> RetArg2<A1, A2, R> of(RetArg2<A1, A2, R> retArg2) {
		return retArg2;
	}

	/**
	 * �����݂��߂�l����̊֐����ARetArg3�ɃL���X�g���܂��B
	 *
	 * @param <A1>    ����1
	 * @param <A2>    ����2
	 * @param <A3>    ����3
	 * @param <R>     �߂�l
	 * @param retArg3 �����݂��߂�l����̊֐�
	 * @return RetArg3
	 */
	public static <A1, A2, A3, R> RetArg3<A1, A2, A3, R> of(RetArg3<A1, A2, A3, R> retArg3) {
		return retArg3;
	}

	/**
	 * �����Ȃ��߂�l�Ȃ��̊֐����AVoidArg0�ɃL���X�g���܂��B
	 *
	 * @param voidArg0 �����Ȃ��߂�l�Ȃ��̊֐�
	 * @return VoidArg0
	 */
	public static VoidArg0 of(VoidArg0 voidArg0) {
		return voidArg0;
	}

	/**
	 * �����ЂƂ߂�l�Ȃ��̊֐����AVoidArg1�ɃL���X�g���܂��B
	 *
	 * @param <A1>     ����1
	 * @param voidArg1 �����ЂƂ߂�l�Ȃ��̊֐�
	 * @return VoidArg1
	 */
	public static <A1> VoidArg1<A1> of(VoidArg1<A1> voidArg1) {
		return voidArg1;
	}

	/**
	 * �����ӂ��߂�l�Ȃ��̊֐����AVoidArg2�ɃL���X�g���܂��B
	 *
	 * @param <A1>     ����1
	 * @param <A2>     ����2
	 * @param voidArg2 �����ӂ��߂�l�Ȃ��̊֐�
	 * @return VoidArg2
	 */
	public static <A1, A2> VoidArg2<A1, A2> of(VoidArg2<A1, A2> voidArg2) {
		return voidArg2;
	}

	/**
	 * �����݂��߂�l�Ȃ��̊֐����AVoidArg2�ɃL���X�g���܂��B
	 *
	 * @param <A1>     ����1
	 * @param <A2>     ����2
	 * @param <A3>     ����3
	 * @param voidArg3 �����݂��߂�l�Ȃ��̊֐�
	 * @return VoidArg3
	 */
	public static <A1, A2, A3> VoidArg3<A1, A2, A3> of(VoidArg3<A1, A2, A3> voidArg3) {
		return voidArg3;
	}

	private Execute() {
	}
}
