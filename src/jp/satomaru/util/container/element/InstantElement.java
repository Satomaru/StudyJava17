package jp.satomaru.util.container.element;

import java.time.Instant;
import java.util.Objects;

import jp.satomaru.util.container.element.parser.ElementParser;

/**
 * インスタントを保持するエレメントです。
 *
 * @author Satomaru
 */
public record InstantElement(Object id, Instant value) implements Element<Instant> {

	public InstantElement {
		Objects.requireNonNull(id, "id");
		Objects.requireNonNull(value, "value");
	}

	@Override
	public Element<?> map(ElementParser<?> parser) throws ElementException {
		return parser.parse(this);
	}

	@Override
	public <P> P parse(ElementParser<P> parser) throws ElementException {
		return parser.parse(this).value();
	}
}
