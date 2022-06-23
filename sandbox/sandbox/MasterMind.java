package sandbox;

import java.util.Arrays;
import java.util.stream.IntStream;

import jp.satomaru.util.BlockFormatter;
import jp.satomaru.util.CharBlockBuilder;
import jp.satomaru.util.Lottery;
import jp.satomaru.util.Matrix;

/**
 * マスターマインド。
 *
 * ただいま作成中……。
 *
 * @author Satomaru
 */
public final class MasterMind {

	public static void main(String[] args) {
		MasterMind me = new MasterMind();
		me.initialize();
		me.show();
	}

	public static final String BACKGROUND = """
		+--+--+--+
		|**|**|**|* *
		+--+--+--+
		|**|**|**|* *
		+--+--+--+
		|**|**|**|* *
		+--+--+--+
		 ** ** **
		 ** ** **
		""";

	private final Matrix<Integer> answer = new Matrix<>(3, 3, Integer[]::new);
	private final Matrix<Integer> field = new Matrix<>(3, 3, Integer[]::new);
	private final Integer[] hHits = new Integer[3];
	private final Integer[] hBlows = new Integer[3];
	private final Integer[] vHits = new Integer[3];
	private final Integer[] vBlows = new Integer[3];
	private final BlockFormatter formatter;

	public MasterMind() {
		CharBlockBuilder background = new CharBlockBuilder(13, 9);
		background.fill(' ');
		background.set(0, 0, BACKGROUND);

		formatter = new BlockFormatter(background);

		IntStream.range(0, 3).forEach(y -> {
			IntStream.range(0, 3).forEach(x -> {
				formatter.addLabel(x * 3 + 1, y * 2 + 1, "%2d", () -> field.get(x, y));
			});
		});

		IntStream.range(0, 3).forEach(index -> {
			formatter.addLabel(10, index * 2 + 1, "%d", () -> hHits[index]);
			formatter.addLabel(12, index * 2 + 1, "%d", () -> hBlows[index]);
			formatter.addLabel(index * 3 + 1, 7, "%2d", () -> vHits[index]);
			formatter.addLabel(index * 3 + 1, 8, "%2d", () -> vBlows[index]);
		});
	}

	public void initialize() {
		Lottery<Integer> values = Lottery.generate(9, 1, previous -> previous + 1);
		answer.fill((x, y) -> values.next());
		field.fill((x, y) -> 1 + x + y * 3);
		judge();
	}

	public void show() {
		System.out.println(formatter.format());
	}

	private boolean judge() {
		Arrays.fill(hHits, 0);
		Arrays.fill(hBlows, 0);
		Arrays.fill(vHits, 0);
		Arrays.fill(vBlows, 0);

		field.forEach((x, y, value) -> {
			if (answer.is(x, y, value)) {
				++hHits[y];
				++vHits[x];
			} else {
				if (answer.row(y).stream().anyMatch(value::equals)) {
					++hBlows[y];
				}

				if (answer.col(x).stream().anyMatch(value::equals)) {
					++vBlows[x];
				}
			}
		});

		return Arrays.stream(hHits).allMatch(hit -> hit == 3);
	}
}
