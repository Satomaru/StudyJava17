package jp.satomaru.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.UnaryOperator;

public class Lottery<E> implements Iterator<E> {

	public static <E> Lottery<E> of(Collection<E> elements) {
		LinkedList<E> list = new LinkedList<>(elements);
		return new Lottery<>(list);
	}

	public static <E> Lottery<E> of(E[] elements) {
		return of(Arrays.asList(elements));
	}

	public static <E> Lottery<E> generate(int size, E start, UnaryOperator<E> next) {
		LinkedList<E> list = new LinkedList<>();
		E element = start;
		list.add(element);

		for (int index = 1; index < size; index++) {
			element = next.apply(element);
			list.add(element);
		}

		return new Lottery<>(list);
	}

	private final LinkedList<E> elements;

	private final Random random = new Random();

	private Lottery(LinkedList<E> elements) {
		this.elements = elements;
	}

	@Override
	public boolean hasNext() {
		return !elements.isEmpty();
	}

	@Override
	public E next() {
		if (elements.isEmpty()) {
			throw new NoSuchElementException();
		}

		int index = random.nextInt(elements.size());
		return elements.remove(index);
	}
}
