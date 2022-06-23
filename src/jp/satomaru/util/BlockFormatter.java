package jp.satomaru.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BlockFormatter {

	private class Label {

		private final int left;

		private final int top;

		private final Supplier<String> bind;

		private Label(int left, int top, Supplier<String> bind) {
			this.left = left;
			this.top = top;
			this.bind = bind;
		}

		private void render(CharBlockBuilder builder) {
			builder.set(left, top, bind.get());
		}
	}

	private final CharBlockBuilder background;

	private final List<Label> labels = new ArrayList<>();

	public BlockFormatter(CharBlockBuilder background) {
		this.background = background;
	}

	public void addLabel(int left, int top, Supplier<String> bind) {
		labels.add(new Label(left, top, bind));
	}

	public void addLabel(int left, int top, String format, Supplier<?> bind) {
		labels.add(new Label(left, top, () -> String.format(format, bind.get())));
	}

	public String format() {
		CharBlockBuilder builder = background.clone();
		labels.forEach(label -> label.render(builder));
		return builder.toString();
	}
}
