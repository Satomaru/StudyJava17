package jp.satomaru.util.container.element.parser;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.satomaru.util.container.element.Element;
import jp.satomaru.util.container.element.ElementException;
import jp.satomaru.util.container.element.ElementException.Type;
import jp.satomaru.util.container.element.InstantElement;
import jp.satomaru.util.container.element.LocalDateTimeElement;
import jp.satomaru.util.container.element.LongElement;
import jp.satomaru.util.container.element.StringElement;

final class ToLocalDateTime extends ElementParser<LocalDateTime> {

	private static final Pattern DATETIME_PATTERN = Pattern.compile(
		"(?<year>\\d{4})[/.-](?<month>\\d{2})[/.-](?<dayOfMonth>\\d{2})"
			+ "([T ](?<hour>\\d{2}):(?<minute>\\d{2}):(?<second>\\d{2})(\\.(?<milliOfSecond>\\d{3}))?)?");

	@Override
	public Element<LocalDateTime> parse(InstantElement element) throws ElementException {
		return map(element, value -> value.atZone(ZoneId.systemDefault()).toLocalDateTime());
	}

	@Override
	public Element<LocalDateTime> parse(LocalDateTimeElement element) throws ElementException {
		return element;
	}

	@Override
	public Element<LocalDateTime> parse(LongElement element) throws ElementException {
		return map(element, value -> Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalDateTime());
	}

	@Override
	public Element<LocalDateTime> parse(StringElement element) throws ElementException {
		return map(element, value -> {
			Matcher matcher = DATETIME_PATTERN.matcher(value);

			if (!matcher.matches()) {
				throw new IllegalArgumentException("not dateTime pattern");
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

	@Override
	protected Element<LocalDateTime> set(Element<?> element, LocalDateTime newValue) {
		return new LocalDateTimeElement(element.id(), newValue);
	}

	@Override
	protected Type typeWhenParseFailure() {
		return Type.PARSE_LOCALDATETIME_FAILURE;
	}
}
