package sandbox;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

import jp.satomaru.util.BlockFormatter;
import jp.satomaru.util.CharBlockBuilder;
import jp.satomaru.util.Lottery;
import jp.satomaru.util.Matrix;
import jp.satomaru.util.component.Component;
import jp.satomaru.util.component.ComponentException;
import jp.satomaru.util.component.Dispatcher;
import jp.satomaru.util.component.ElementSet;
import jp.satomaru.util.component.element.mapper.ElementMapper;

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

		try (Scanner scanner = new Scanner(System.in)) {
			boolean gameOver;

			do {
				me.show();
				System.out.println("Command? (set x y value | end | quit)");
				ElementSet elements = COMPONENT.elements(scanner, "command", "arg1", "arg2", "arg3");

				gameOver = DISPATCHER.run(me, elements)
					.ifPresentLeft(exception -> System.out.println(exception.getLocalizedMessage()))
					.optionalRight()
					.orElse(false);

				System.out.println();
			} while (!gameOver);
		}

		if (me.succeeded) {
			System.out.println("Congratulations!");
		} else {
			System.out.println("Game over.");
		}
	}

	private static final String BACKGROUND = """
		Turn: *

		   0  1  2
		 +--+--+--+
		0|**|**|**|* *
		 +--+--+--+
		1|**|**|**|* *
		 +--+--+--+
		2|**|**|**|* *
		 +--+--+--+
		  ** ** **
		  ** ** **
		""";

	private static final Component<MasterMind> COMPONENT = new Component<>(MasterMind.class);

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
	private boolean succeeded;

	public MasterMind() {
		CharBlockBuilder background = new CharBlockBuilder(14, 12);
		background.fill(' ');
		background.set(0, 0, BACKGROUND);

		formatter = new BlockFormatter(background);
		formatter.addLabel(6, 0, "%d", () -> turn[0]);

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
		var parser = elements.parseAndRenameOrThrowBy(ElementMapper.INTEGER);
		int x = parser.parseAndRename("arg1", "x");
		int y = parser.parseAndRename("arg2", "y");
		int value = parser.parseAndRename("arg3", "value");
		field.findAny(value).ifPresent(cell -> cell.swap(x, y));
		return false;
	}

	public boolean end(ElementSet elements) throws ComponentException {
		++turn[0];
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
