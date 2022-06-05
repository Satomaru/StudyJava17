package sandbox;

import java.util.ArrayList;

import jp.satomaru.util.function.Execute;

public class VoidArg2Sample {

	public static void main(String[] args) {
		var me = new VoidArg2Sample();

		String result1 = Execute.of(me::set)
			.run(0, "new0")
			.map(Exception::getMessage)
			.orElse("succeeded");

		String result2 = Execute.of(me::set)
			.run(1, "new1")
			.map(Exception::getMessage)
			.orElse("succeeded");

		// STDOUT> set(0, "new0") -> succeeded
		System.out.println(String.format("set(0, \"new0\") -> %s", result1));

		// STDOUT> set(1, "new1") -> Index 1 out of bounds for length 1
		System.out.println(String.format("set(1, \"new1\") -> %s", result2));
	}

	private final ArrayList<String> list = new ArrayList<>();

	public VoidArg2Sample() {
		list.add("element0");
	}

	public void set(int index, String element) {
		list.set(index, element);
	}
}
