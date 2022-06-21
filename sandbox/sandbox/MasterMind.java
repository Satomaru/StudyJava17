package sandbox;

import java.util.Arrays;

import jp.satomaru.util.Lottery;
import jp.satomaru.util.Matrix;
import jp.satomaru.util.console.Std;

/**
 * マスターマインド。
 *
 * ただいま作成中……。
 *
 * @author Satomaru
 */
public final class MasterMind {

	public static void main(String[] args) {
		MasterMind me = new MasterMind(5, 5);
		me.initialize();
		me.show();
	}

	private final int width;
	private final int count;
	private final Matrix<Integer> answer;
	private final Matrix<Integer> field;
	private final Integer[] hHits;
	private final Integer[] hBlows;
	private final Integer[] vHits;
	private final Integer[] vBlows;

	public MasterMind(int width, int height) {
		this.width = width;
		count = width * height;
		answer = new Matrix<>(width, height, Integer[]::new);
		field = new Matrix<>(width, height, Integer[]::new);
		hHits = new Integer[height];
		hBlows = new Integer[height];
		vHits = new Integer[width];
		vBlows = new Integer[width];
	}

	public void initialize() {
		Lottery<Integer> values = Lottery.generate(count, 1, previous -> previous + 1);
		answer.fill((x, y) -> values.next());
		field.fill((x, y) -> 1 + x + y * width);
		judge();
	}

	public void show() {
		Std.out.writeLine("+", "--+".repeat(width));

		field.forEachRow((y, row) -> {
			row.forEach((x, value) -> Std.out.format("|%2d", value));
			Std.out.formatLine("| %2d %2d", hHits[y], hBlows[y]);
			Std.out.writeLine("+", "--+".repeat(width));
		});

		Arrays.stream(vHits).forEach(hit -> Std.out.format(" %2d", hit));
		Std.out.writeLine();

		Arrays.stream(vBlows).forEach(blow -> Std.out.format(" %2d", blow));
		Std.out.writeLine();
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

		return Arrays.stream(hHits).allMatch(hit -> hit == width);
	}
}
