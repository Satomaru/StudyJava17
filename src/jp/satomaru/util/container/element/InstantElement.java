package jp.satomaru.util.container.element;

import java.time.Instant;
import java.util.Objects;

import jp.satomaru.util.container.element.parser.ElementParser;

/**
 * インスタントを保持するエレメントです。
 *
 * @author Satomaru
 */
public record InstantElement(String id, Instant value) implements Element<Instant> {

	public InstantElement {
		Objects.requireNonNull(id, "id");
	}

	@Override
	public <E extends Element<?>> E accept(ElementParser<?, E> parser) throws ElementException {
		return parser.parse(this);
	}
}
