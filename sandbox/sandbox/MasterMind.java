package sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import jp.satomaru.util.Lottery;
import jp.satomaru.util.Strings;
import jp.satomaru.util.Tester;
import jp.satomaru.util.component.Component;
import jp.satomaru.util.component.ComponentException;
import jp.satomaru.util.component.ComponentId;
import jp.satomaru.util.component.Dispatcher;
import jp.satomaru.util.component.ElementSet;
import jp.satomaru.util.component.element.mapper.ElementMapper;
import jp.satomaru.util.container.BlockArray;
import jp.satomaru.util.container.BlockPatcher;
import jp.satomaru.util.io.LineAppender;

/**
 * マスターマインド。
 *
 * <p>
 * 3x3マスの数を当てるゲームです。
 * マスの各行と各列には、ヒントとして H(it)とB(low)が表示されます。
 * Hitは、正解の数を表します。
 * Blowは、正解ではないけど、同列または同行の違うマスであれば正解になる数を表します。
 *
 * <p>
 * setコマンドを実行すると、指定したマスに、指定した値を設定します。
 * この時、直前までその値が指定されていたマスには、今回指定したマスの値が設定されます。
 *
 * <p>
 * マスの値を設定した後、endコマンドでターンを終了します。
 * ここで正解が判定され、見事、全てのマスで正解した時はゲームクリアとなります。
 *
 * @author Satomaru
 */
public final class MasterMind {

	public static void main(String[] args) {
		var output = LineAppender.sysout();
		var prompt = Component.getMessage(ID, "PROMPT", "Command? (set <x> <y> <value> | end | quit)");
		var succeeded = Component.getMessage(ID, "SUCCEEDED", "Congratulations!");
		var failed = Component.getMessage(ID, "FAILED", "Game over.");
		var me = new MasterMind();

		me.initialize();

		try (Scanner scanner = new Scanner(System.in)) {
			boolean gameOver;

			do {
				me.show(output);
				output.newLine();
				output.writeLine(prompt);
				var elements = COMPONENT.elements(scanner, "command", "arg1", "arg2", "arg3");

				gameOver = DISPATCHER.run(me, elements)
					.ifPresentLeft(output::localizedMessage)
					.optionalRight()
					.orElse(false);

				output.newLine();
			} while (!gameOver);
		}

		me.show(output);
		output.newLine();

		if (me.isSucceeded()) {
			output.writeLine(succeeded);
		} else {
			output.writeLine(failed);
		}
	}

	private static final Character[][] BACKGROUND = Strings.charBlock("""
		Turn:***

		   0  1  2
		 +--+--+--+
		0| *| *| *|* *
		 +--+--+--+
		1| *| *| *|* *
		 +--+--+--+
		2| *| *| *|* *
		 +--+--+--+
		   *  *  * H
		   *  *  *   B
		""");

	private static final Component<MasterMind> COMPONENT = new Component<>(MasterMind.class);

	private static final ComponentId ID = COMPONENT.id("main");

	private static final Dispatcher<MasterMind, Boolean> DISPATCHER = COMPONENT
		.dispatcher("set", MasterMind::set)
		.append("end", MasterMind::end)
		.append("quit", MasterMind::quit)
		.end("command");

	private final BlockArray<Character> board = BlockArray.character(14, 12);
	private final BlockArray<Integer> answer = BlockArray.integer(3, 3);
	private final BlockArray<Integer> field = BlockArray.integer(3, 3);
	private final List<BlockPatcher<Character>> patchers = new ArrayList<>();
	private final Integer[] hHits = new Integer[3];
	private final Integer[] hBlows = new Integer[3];
	private final Integer[] vHits = new Integer[3];
	private final Integer[] vBlows = new Integer[3];
	private final Integer[] turn = new Integer[1];
	private final Boolean[] turnBegan = new Boolean[1];
	private boolean succeeded;

	public MasterMind() {
		patchers.add(BlockPatcher.chars(5, 0, "%2d", turn, 0));
		patchers.add(BlockPatcher.cell(7, 0, () -> turnBegan[0] ? '*' : ' '));

		IntStream.range(0, 3).forEach(y -> {
			IntStream.range(0, 3).forEach(x -> {
				Supplier<Character> src = () -> (char) ('0' + field.cell(x, y).get());
				patchers.add(BlockPatcher.cell(x * 3 + 3, y * 2 + 4, src));
			});
		});

		IntStream.range(0, 3).forEach(index -> {
			patchers.add(BlockPatcher.cell(11, index * 2 + 4, () -> (char) ('0' + hHits[index])));
			patchers.add(BlockPatcher.cell(13, index * 2 + 4, () -> (char) ('0' + hBlows[index])));
			patchers.add(BlockPatcher.cell(index * 3 + 3, 10, () -> (char) ('0' + vHits[index])));
			patchers.add(BlockPatcher.cell(index * 3 + 3, 11, () -> (char) ('0' + vBlows[index])));
		});
	}

	public void initialize() {
		field.fill((x, y, value) -> 1 + x + y * 3);
		turn[0] = 1;
		turnBegan[0] = true;
		succeeded = false;

		do {
			Lottery<Integer> values = Lottery.generate(9, 1, previous -> previous + 1);
			answer.fill(values::next);
		} while (judge());
	}

	public void show(LineAppender<?> output) {
		board.set(0, 0, BACKGROUND);
		patchers.forEach(board::patch);
		board.appendTo(output);
	}

	public boolean set(ElementSet elements) throws ComponentException {
		var mapper = elements.renameAndMap(ElementMapper.INTEGER);
		int x = mapper.map("arg1", "x").must(Tester.range(0, 3)).get();
		int y = mapper.map("arg2", "y").must(Tester.range(0, 3)).get();
		int value = mapper.map("arg3", "value").must(Tester.range(1, 10)).get();

		var target = field.cell(x, y);
		field.findAny(value).ifPresent(target::swap);
		turnBegan[0] = false;
		return false;
	}

	public boolean end(ElementSet elements) throws ComponentException {
		++turn[0];
		turnBegan[0] = true;
		succeeded = judge();
		return succeeded || turn[0] > 10;
	}

	public boolean quit(ElementSet elements) throws ComponentException {
		succeeded = false;
		return true;
	}

	public boolean isSucceeded() {
		return succeeded;
	}

	private boolean judge() {
		Arrays.fill(hHits, 0);
		Arrays.fill(hBlows, 0);
		Arrays.fill(vHits, 0);
		Arrays.fill(vBlows, 0);

		field.forEach((x, y, value) -> {
			if (answer.cell(x, y).get() == value) {
				++hHits[y];
				++vHits[x];
			} else {
				if (answer.row(y).anyMatch(value)) {
					++hBlows[y];
				}

				if (answer.col(x).anyMatch(value)) {
					++vBlows[x];
				}
			}
		});

		return Arrays.stream(hHits).allMatch(hit -> hit == 3);
	}
}
