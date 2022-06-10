package jp.satomaru.util.container.element.parser;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.satomaru.util.container.element.Element;
import jp.satomaru.util.container.element.BooleanElement;
import jp.satomaru.util.container.element.DoubleElement;
import jp.satomaru.util.container.element.ElementException;
import jp.satomaru.util.container.element.ElementException.Type;
import jp.satomaru.util.container.element.InstantElement;
import jp.satomaru.util.container.element.IntegerElement;
import jp.satomaru.util.container.element.LocalDateTimeElement;
import jp.satomaru.util.container.element.LongElement;
import jp.satomaru.util.container.element.StringElement;
import jp.satomaru.util.function.RetArg1;

final class ToLocalDateTime implements ElementParser<LocalDateTime, LocalDateTimeElement> {

	private static final Pattern DATETIME_PATTERN = Pattern.compile(
		"(?<year>\\d{4})[/.-](?<month>\\d{2})[/.-](?<dayOfMonth>\\d{2})"
			+ "([T ](?<hour>\\d{2}):(?<minute>\\d{2}):(?<second>\\d{2})(\\.(?<milliOfSecond>\\d{3}))?)?");

	@Override
	public LocalDateTimeElement set(Element<?> element, LocalDateTime newValue) {
		return Element.of(element.id(), newValue);
	}

	@Override
	public <F> LocalDateTimeElement parse(Element<F> element, RetArg1<F, LocalDateTime> parser)
		throws ElementException {

		try {
			return set(element, element.isEmpty() ? null : parser.execute(element.value()));
		} catch (ElementException e) {
			throw e;
		} catch (Exception e) {
			throw new ElementException(element, Type.PARSE_LOCALDATETIME_FAILURE, e);
		}
	}

	@Override
	public LocalDateTimeElement parse(BooleanElement element) throws ElementException {
		throw new ElementException(element, Type.PARSE_LOCALDATETIME_FAILURE);
	}

	@Override
	public LocalDateTimeElement parse(DoubleElement element) throws ElementException {
		throw new ElementException(element, Type.PARSE_LOCALDATETIME_FAILURE);
	}

	@Override
	public LocalDateTimeElement parse(InstantElement element) throws ElementException {
		return parse(element, value -> value.atZone(ZoneId.systemDefault()).toLocalDateTime());
	}

	@Override
	public LocalDateTimeElement parse(IntegerElement element) throws ElementException {
		throw new ElementException(element, Type.PARSE_LOCALDATETIME_FAILURE);
	}

	@Override
	public LocalDateTimeElement parse(LocalDateTimeElement element) throws ElementException {
		return element;
	}

	@Override
	public LocalDateTimeElement parse(LongElement element) throws ElementException {
		return parse(element, value -> Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalDateTime());
	}

	@Override
	public LocalDateTimeElement parse(StringElement element) throws ElementException {
		return parse(element, value -> {
			Matcher matcher = DATETIME_PATTERN.matcher(value);

			if (!matcher.matches()) {
				throw new ElementException(element, Type.PARSE_LOCALDATETIME_FAILURE);
			}

			var year = Integer.valueOf(matcher.group("year"));
			var month = Integer.valueOf(matcher.group("month"));
			var dayOfMonth = Integer.valueOf(matcher.group("dayOfMonth"));
			var hour = Optional.ofNullable(matcher.group("hour")).map(Integer::parseInt).orElse(0);
			var minute = Optional.ofNullable(matcher.group("minute")).map(Integer::parseInt).orElse(0);
			var second = Optional.ofNullable(matcher.group("second")).map(Integer::parseInt).orElse(0);
			var milliOfSecond = Optional.ofNullable(matcher.group("milliOfSecond")).map(Integer::parseInt).orElse(0);
			return LocalDateTime.of(year, month, dayOfMonth, hour, minute, second, milliOfSecond * 1_000_000);
		});
	}
}
