package jp.satomaru.util;

import java.util.HashMap;
import java.util.Map;

import jp.satomaru.util.function.RetArg2;
import jp.satomaru.util.function.RetArg3;

/**
 * 指定されたコマンドに対応する関数を選択する、ディスパッチャーを作成します。
 *
 * @author Satomaru
 * @param <M> 実行する関数を持つオブジェクト
 * @param <A> 関数の引数
 * @param <C> コマンド
 * @param <R> 結果
 */
public final class Dispatcher<M, A, C, R> {

	/**
	 * コマンドと、それに対応する関数を指定して、ディスパッチャーの作成を開始します。
	 *
	 * @param <M>       実行する関数を持つオブジェクト
	 * @param <A>       関数の引数
	 * @param <C>       コマンド
	 * @param <R>       結果
	 * @param command   コマンド
	 * @param processor 実行する関数
	 * @return ディスパッチャーを作成するオブジェクト
	 */
	public static final <M, A, C, R> Dispatcher<M, A, C, R> begin(C command, RetArg2<M, A, R> processor) {
		var dispatcher = new Dispatcher<M, A, C, R>();
		dispatcher.append(command, processor);
		return dispatcher;
	}

	/** 関数マップ。 */
	private final HashMap<C, RetArg2<M, A, R>> processors = new HashMap<>();

	private Dispatcher() {
	}

	/**
	 * コマンドと、それに対応する関数を追加します。
	 *
	 * @param command   コマンド
	 * @param processor 実行する関数
	 * @return このオブジェクト自身
	 */
	public Dispatcher<M, A, C, R> append(C command, RetArg2<M, A, R> processor) {
		processors.put(command, processor);
		return this;
	}

	/**
	 * ディスパッチャーの作成を終了します。
	 *
	 * @return ディスパッチャー
	 */
	public RetArg3<M, A, C, R> end() {
		Map<C, RetArg2<M, A, R>> fixed = Map.copyOf(processors);

		return (model, argument, command) -> {
			if (command == null) {
				throw new NullPointerException("command");
			}

			if (!fixed.containsKey(command)) {
				throw new IllegalArgumentException(String.format("command: %s", command));
			}

			return fixed.get(command).execute(model, argument);
		};
	}
}
