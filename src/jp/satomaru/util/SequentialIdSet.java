package jp.satomaru.util;

import java.util.NoSuchElementException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * インデックスに変換可能なIDの集合です。
 *
 * @param <I> ID
 * @param <S> このセットを実装したクラス自身
 * @author Satomaru
 */
public interface SequentialIdSet<I, S extends SequentialIdSet<I, S>> {

	/**
	 * IDの総数を取得します。
	 *
	 * @return IDの総数
	 */
	int size();

	/**
	 * IDのインデックスを数えます。
	 *
	 * <p>
	 * インデックスは必ず、0以上 {@link #size()} 未満となります。
	 * なお、指定されたIDが存在しない場合は、
	 * {@link NoSuchElementException} がスローされます。
	 *
	 * @param id ID
	 * @return インデックス
	 */
	int seek(I id);

	/**
	 * IDを取得します。
	 *
	 * <p>
	 * インデックスに、0未満、または {@link #size()} 以上が指定された場合は
	 * {@link NoSuchElementException} がスローされます。
	 *
	 * @param index インデックス
	 * @return ID
	 */
	I pick(int index);

	/**
	 * 指定されたIDが含まれていることを判定します。
	 *
	 * @param id ID
	 * @return IDが含まれている場合はtrue
	 */
	boolean contains(I id);

	/**
	 * 指定されたIDセットのIDが全て含まれていることを判定します。
	 *
	 * @param set IDセット
	 * @return IDが全て含まれている場合はtrue
	 */
	boolean contains(S set);

	/**
	 * 指定されたIDが含まれていることを検査します。
	 *
	 * <p>
	 * 指定されたIDがこのセットに含まれていない場合は
	 * {@link NoSuchElementException} をスローします。
	 *
	 * @param id ID
	 * @return 引数のIDをそのまま返します。
	 */
	default I validate(I id) {
		if (!contains(id)) {
			throw new NoSuchElementException();
		}

		return id;
	}

	/**
	 * 指定されたIDセットのIDが全て含まれていることを検査します。
	 *
	 * <p>
	 * 指定されたIDセットのIDが1つでも含まれていない場合は
	 * {@link NoSuchElementException} をスローします。
	 *
	 * @param set IDセット
	 * @return 引数のIDセットをそのまま返します。
	 */
	default S validate(S set) {
		if (!contains(set)) {
			throw new NoSuchElementException();
		}

		return set;
	}

	/**
	 * IDのストリームを取得します。
	 *
	 * @return インデックス昇順のIDストリーム
	 */
	default Stream<I> stream() {
		return IntStream.range(0, size()).mapToObj(this::pick);
	}
}
