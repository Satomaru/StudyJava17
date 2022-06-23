package jp.satomaru.util.component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import jp.satomaru.util.component.ComponentException.ErrorCode;
import jp.satomaru.util.component.element.BooleanElement;
import jp.satomaru.util.component.element.DoubleElement;
import jp.satomaru.util.component.element.Element;
import jp.satomaru.util.component.element.EmptyElement;
import jp.satomaru.util.component.element.InstantElement;
import jp.satomaru.util.component.element.IntegerElement;
import jp.satomaru.util.component.element.LocalDateTimeElement;
import jp.satomaru.util.component.element.LongElement;
import jp.satomaru.util.component.element.StringElement;

public final class Component<M> {

	public final class DispatcherBuilder<R> {

		private final HashMap<String, Dispatcher<M, R>> procedures = new HashMap<>();

		private DispatcherBuilder(String command, Dispatcher<M, R> procedure) {
			procedures.put(command, procedure);
		}

		public DispatcherBuilder<R> append(String command, Dispatcher<M, R> procedure) {
			procedures.put(command, procedure);
			return this;
		}

		public Dispatcher<M, R> end(String commandName) {
			var fixed = Map.copyOf(procedures);

			return (model, elements) -> {
				Object command = elements.get(commandName).value();

				if (command == null) {
					throw new ComponentException(id(commandName), ErrorCode.EMPTY, command);
				}

				if (!fixed.containsKey(command)) {
					throw new ComponentException(id(commandName), ErrorCode.UNKNOWN_COMMAND, command);
				}

				return fixed.get(command).apply(model, elements);
			};
		}
	}

	private static final Pattern SPACE_PATTERN = Pattern.compile("\\s+");

	private final Class<M> type;

	public Component(Class<M> type) {
		this.type = type;
	}

	public ComponentId id(String name) {
		return new ComponentId(type, name);
	}

	public <R> DispatcherBuilder<R> dispatcher(String command, Dispatcher<M, R> procedure) {
		return new DispatcherBuilder<>(command, procedure);
	}

	public Element<?> element(String name, Object value) {
		if (value == null) {
			return new EmptyElement<>(id(name));
		}

		if (value instanceof Boolean booleanValue) {
			return new BooleanElement(id(name), booleanValue);
		}

		if (value instanceof Double doubleValue) {
			return new DoubleElement(id(name), doubleValue);
		}

		if (value instanceof Instant instant) {
			return new InstantElement(id(name), instant);
		}

		if (value instanceof Integer integer) {
			return new IntegerElement(id(name), integer);
		}

		if (value instanceof LocalDateTime localDateTime) {
			return new LocalDateTimeElement(id(name), localDateTime);
		}

		if (value instanceof Long longValue) {
			return new LongElement(id(name), longValue);
		}

		if (value instanceof String string) {
			return new StringElement(id(name), string);
		}

		String type = value.getClass().getSimpleName();
		throw new IllegalArgumentException(String.format("unsupported type: %s", type));
	}

	public ElementSet elements(Map<String, ?> values) {
		HashMap<String, Element<?>> elements = new HashMap<>();

		for (Map.Entry<String, ?> entry : values.entrySet()) {
			elements.put(entry.getKey(), element(entry.getKey(), entry.getValue()));
		}

		return new ElementSet(type, elements);
	}

	public ElementSet elements(Iterable<?> values, String... names) {
		HashMap<String, Object> map = new HashMap<>();
		Iterator<?> iterator = values.iterator();

		for (String name : names) {
			Object value = iterator.hasNext() ? iterator.next() : null;
			map.put(name, value);
		}

		return elements(map);
	}

	public ElementSet elements(Object[] values, String... names) {
		return elements(Arrays.asList(values), names);
	}

	public ElementSet elements(Scanner scanner, String... names) {
		String[] ssv = SPACE_PATTERN.split(scanner.nextLine());
		return elements(ssv, names);
	}
}
