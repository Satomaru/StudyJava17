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
		MasterMind me = new MasterMind();
		me.initialize();
		me.show();
	}

	private final Matrix<Integer> answer = new Matrix<>(3, 3, Integer[]::new);
	private final Matrix<Integer> field = new Matrix<>(3, 3, Integer[]::new);
	private final Integer[] hHits = new Integer[3];
	private final Integer[] hBlows = new Integer[3];
	private final Integer[] vHits = new Integer[3];
	private final Integer[] vBlows = new Integer[3];

	public void initialize() {
		Lottery<Integer> values = Lottery.generate(9, 1, previous -> previous + 1);
		answer.fill((x, y) -> values.next());
		field.fill((x, y) -> 1 + x + y * 3);
		judge();
	}

	public void show() {
		Std.out.writeLine("+-+-+-+");

		field.forEachRow((y, row) -> {
			row.forEach((x, value) -> Std.out.write("|", value));
			Std.out.writeLine("| ", hHits[y], " ", hBlows[y]);
			Std.out.writeLine("+-+-+-+");
		});

		Std.out.write(" ").joinLine(vHits, " ");
		Std.out.write(" ").joinLine(vBlows, " ");
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
