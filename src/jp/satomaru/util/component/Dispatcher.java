package jp.satomaru.util.component;

import java.util.HashMap;
import java.util.Map;

import jp.satomaru.util.component.DispatcherException.ErrorCode;
import jp.satomaru.util.component.element.ElementSet;
import jp.satomaru.util.function.ThrowableBiFunction;

@FunctionalInterface
public interface Dispatcher<M, R> extends ThrowableBiFunction<M, ElementSet, R, ComponentException> {

	public static final class Builder<M, R> {

		public static final <M, R> Builder<M, R> begin(Object command, Dispatcher<M, R> procedure) {
			var builder = new Builder<M, R>();
			builder.append(command, procedure);
			return builder;
		}

		private final HashMap<Object, Dispatcher<M, R>> procedures = new HashMap<>();

		private Builder() {
		}

		public Builder<M, R> append(Object command, Dispatcher<M, R> procedure) {
			procedures.put(command, procedure);
			return this;
		}

		public Dispatcher<M, R> end(String commandName) {
			var fixed = Map.copyOf(procedures);

			return (model, elements) -> {
				Object command = elements.get(commandName).value();

				if (command == null) {
					throw new DispatcherException(commandName, ErrorCode.EMPTY_COMMAND, command);
				}

				if (!fixed.containsKey(command)) {
					throw new DispatcherException(commandName, ErrorCode.UNKNOWN_COMMAND, command);
				}

				return fixed.get(command).apply(model, elements);
			};
		}
	}
}
