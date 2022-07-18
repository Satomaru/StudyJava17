package jp.satomaru.util.io;

import java.io.IOException;

public class IOSilencer<T> {

	@FunctionalInterface
	public interface Output<T> {
		void write(T target) throws IOException;
	}

	@FunctionalInterface
	public interface OutputArg<T, A> {
		void write(T target, A arg) throws IOException;

		default Output<T> inject(A arg) {
			return target -> write(target, arg);
		}
	}

	@FunctionalInterface
	public interface OutputArgs<T, A1, A2> {
		void write(T target, A1 arg1, A2 arg2) throws IOException;

		default Output<T> inject(A1 arg1, A2 arg2) {
			return target -> write(target, arg1, arg2);
		}
	}

	@FunctionalInterface
	public interface Input<T, R> {
		R read(T target) throws IOException;
	}

	@FunctionalInterface
	public interface InputArg<T, A, R> {
		R read(T target, A arg) throws IOException;

		default Input<T, R> inject(A arg) {
			return target -> read(target, arg);
		}
	}

	@FunctionalInterface
	public interface InputArgs<T, A1, A2, R> {
		R read(T target, A1 arg1, A2 arg2) throws IOException;

		default Input<T, R> inject(A1 arg1, A2 arg2) {
			return target -> read(target, arg1, arg2);
		}
	}

	private final T target;

	private IOException exception;

	public IOSilencer(T target) {
		this.target = target;
	}

	public T getTarget() {
		return target;
	}

	public boolean isThrown() {
		return exception != null;
	}

	public IOException getException() {
		return exception;
	}

	public void write(Output<T> action) {
		if (!isThrown()) {
			try {
				action.write(target);
			} catch (IOException e) {
				exception = e;
			}
		}
	}

	public <A> void write(OutputArg<T, ? super A> action, A arg) {
		write(action.inject(arg));
	}

	public <A1, A2> void write(OutputArgs<T, ? super A1, ? super A2> action, A1 arg1, A2 arg2) {
		write(action.inject(arg1, arg2));
	}

	public <R> R read(Input<T, R> action) {
		if (!isThrown()) {
			try {
				return action.read(target);
			} catch (IOException e) {
				exception = e;
			}
		}

		return null;
	}

	public <A, R> R read(InputArg<T, ? super A, R> action, A arg) {
		return read(action.inject(arg));
	}

	public <A1, A2, R> R read(InputArgs<T, ? super A1, ? super A2, R> action, A1 arg1, A2 arg2) {
		return read(action.inject(arg1, arg2));
	}
}
