package sandbox;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Test {

	public static void main(String[] args) {
		Character[] characters = new Character[] {
			'a', 'b', 'c', 'd', 'e', 'f', 'g'
		};

		String result = Arrays.stream(characters).map(Object::toString).collect(Collectors.joining());
		System.out.println(result);
	}
}
