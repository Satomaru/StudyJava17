package jp.satomaru.util.container.element;

import java.util.Objects;

import jp.satomaru.util.container.element.parser.ElementParser;

/**
 * 文字列を保持するエレメントです。
 *
 * @author Satomaru
 */
public record StringElement(String id, String value) implements Element<String> {

	public StringElement {
		Objects.requireNonNull(id, "id");
	}

	@Override
	public <E extends Element<?>> E accept(ElementParser<?, E> parser) throws ElementException {
		return parser.parse(this);
	}
}
