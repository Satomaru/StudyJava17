package sandbox;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

import jp.satomaru.util.BlockFormatter;
import jp.satomaru.util.CharBlockBuilder;
import jp.satomaru.util.Lottery;
import jp.satomaru.util.Matrix;
import jp.satomaru.util.Tester;
import jp.satomaru.util.component.Component;
import jp.satomaru.util.component.ComponentException;
import jp.satomaru.util.component.ComponentId;
import jp.satomaru.util.component.Dispatcher;
import jp.satomaru.util.component.ElementSet;
import jp.satomaru.util.component.element.mapper.ElementMapper;

/**
 * マスターマインド。
 *
 * <p>
 * 3x3マスの数値を当てるゲームです。マスの各行と各列には、ヒントとして H(it)とB(low)が表示されます。Hitは、正解の数を表します。
 * Blowは、正解ではないけど同列または同行の違うマスであれば正解になる数を表します。
 *
 * <p>
 * setコマンドを実行すると、指定したマスに、指定した値を設定します。 この時、指定した値が設定されていたマスには、
 * 指定したマスに設定されていた値が設定されます。 つまり、「指定したマス」と「指定した値が設定されていたマス」の値が交換されます。
 *
 * <p>
 * マスの値を設定した後、endコマンドでターンを終了します。 ここで正解が判定され、見事、全てのマスで正解した時はゲームクリアとなります。
 *
 * @author Satomaru
 */
public final class MasterMind {

	public static void main(String[] args) {
		String prompt = Component.getMessage(ID, "PROMPT", "Command? (set x y value | end | quit)");
		String succeeded = Component.getMessage(ID, "SUCCEEDED", "Congratulations!");
		String failed = Component.getMessage(ID, "FAILED", "Game over.");

		MasterMind me = new MasterMind();
		me.initialize();

		try (Scanner scanner = new Scanner(System.in)) {
			boolean gameOver;

			do {
				me.show();
				System.out.println(prompt);
				ElementSet elements = COMPONENT.elements(scanner, "command", "arg1", "arg2", "arg3");

				gameOver = DISPATCHER.run(me, elements)
					.ifPresentLeft(exception -> System.out.println(exception.getLocalizedMessage()))
					.optionalRight()
					.orElse(false);

				System.out.println();
			} while (!gameOver);
		}

		me.show();

		if (me.succeeded) {
			System.out.println(succeeded);
		} else {
			System.out.println(failed);
		}
	}

	private static final String BACKGROUND = """
		Turn:***

		   0  1  2
		 +--+--+--+
		0|**|**|**|* *
		 +--+--+--+
		1|**|**|**|* *
		 +--+--+--+
		2|**|**|**|* *
		 +--+--+--+
		  ** ** ** H
		  ** ** **   B
		""";

	private static final Component<MasterMind> COMPONENT = new Component<>(MasterMind.class);

	private static final ComponentId ID = COMPONENT.id("main");

	private static final Dispatcher<MasterMind, Boolean> DISPATCHER = COMPONENT
		.dispatcher("set", MasterMind::set)
		.append("end", MasterMind::end)
		.append("quit", MasterMind::quit)
		.end("command");

	private final Matrix<Integer> answer = new Matrix<>(3, 3, Integer[]::new);
	private final Matrix<Integer> field = new Matrix<>(3, 3, Integer[]::new);
	private final Integer[] hHits = new Integer[3];
	private final Integer[] hBlows = new Integer[3];
	private final Integer[] vHits = new Integer[3];
	private final Integer[] vBlows = new Integer[3];
	private final BlockFormatter formatter;
	private final Integer[] turn = new Integer[1];
	private final Boolean[] turnBegan = new Boolean[1];
	private boolean succeeded;

	public MasterMind() {
		CharBlockBuilder background = new CharBlockBuilder(14, 12);
		background.fill(' ');
		background.set(0, 0, BACKGROUND);

		formatter = new BlockFormatter(background);
		formatter.addLabel(5, 0, "%2d", () -> turn[0]);
		formatter.addLabel(7, 0, "%s", () -> turnBegan[0] ? "*" : " ");

		IntStream.range(0, 3).forEach(y -> {
			IntStream.range(0, 3).forEach(x -> {
				formatter.addLabel(x * 3 + 2, y * 2 + 4, "%2d", () -> field.get(x, y));
			});
		});

		IntStream.range(0, 3).forEach(index -> {
			formatter.addLabel(11, index * 2 + 4, "%d", () -> hHits[index]);
			formatter.addLabel(13, index * 2 + 4, "%d", () -> hBlows[index]);
			formatter.addLabel(index * 3 + 2, 10, "%2d", () -> vHits[index]);
			formatter.addLabel(index * 3 + 2, 11, "%2d", () -> vBlows[index]);
		});
	}

	public void initialize() {
		turn[0] = 1;
		turnBegan[0] = true;
		field.fill((x, y) -> 1 + x + y * 3);

		do {
			Lottery<Integer> values = Lottery.generate(9, 1, previous -> previous + 1);
			answer.fill((x, y) -> values.next());
		} while (judge());
	}

	public void show() {
		System.out.println(formatter.format());
	}

	public boolean set(ElementSet elements) throws ComponentException {
		var parser = elements.renameAndMap(ElementMapper.INTEGER);
		int x = parser.map("arg1", "x").validate(Tester.range(0, 2)).orElseThrow();
		int y = parser.map("arg2", "y").validate(Tester.range(0, 2)).orElseThrow();
		int value = parser.map("arg3", "value").validate(Tester.range(1, 9)).orElseThrow();
		field.findAny(value).ifPresent(cell -> cell.swap(x, y));
		turnBegan[0] = false;
		return false;
	}

	public boolean end(ElementSet elements) throws ComponentException {
		++turn[0];
		turnBegan[0] = true;
		succeeded = judge();
		return succeeded;
	}

	public boolean quit(ElementSet elements) throws ComponentException {
		succeeded = false;
		return true;
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
