package jp.satomaru.util.container.element;

import java.util.Objects;

import jp.satomaru.util.container.element.parser.ElementParser;

/**
 * 整数を保持するエレメントです。
 *
 * @author Satomaru
 */
public record IntegerElement(String id, Integer value) implements Element<Integer> {

	public IntegerElement {
		Objects.requireNonNull(id, "id");
	}

	@Override
	public <E extends Element<?>> E accept(ElementParser<?, E> parser) throws ElementException {
		return parser.parse(this);
	}
}
