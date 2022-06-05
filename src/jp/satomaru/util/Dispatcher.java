package jp.satomaru.util;

import java.util.HashMap;
import java.util.function.Function;

import jp.satomaru.util.function.RetArg1;
import jp.satomaru.util.function.RetArg2;

@FunctionalInterface
public interface Dispatcher<M, A, R> extends Function<String, RetArg2<M, A, R>> {

	public static final class Factory<M, A, R> {

		private final HashMap<String, RetArg2<M, A, R>> commands = new HashMap<>();

		public Factory<M, A, R> add(String key, RetArg2<M, A, R> command) {
			commands.put(key, command);
			return this;
		}

		public Dispatcher<M, A, R> end() {
			HashMap<String, RetArg2<M, A, R>> fixed = new HashMap<>(commands);
			return fixed::get;
		}
	}

	default RetArg1<String, R> inject(M model, A arg) {
		return key -> apply(key).execute(model, arg);
	}
}
