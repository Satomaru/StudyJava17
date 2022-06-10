package jp.satomaru.util.container;

public interface IdentifiableValue<I, V> extends Identifiable<I> {

	V value();

	default String description() {
		String type = (value() == null) ? "?" : value().getClass().getSimpleName();
		return String.format("{<%s> %s: %s}", type, id(), value());
	}
}
