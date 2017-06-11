package org.light.justwaker.utility;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateTimeUtility {

	private static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";
	private static final String DATE_TIME_TEMPLATE = "%02d-%02d-%4d %02d:%02d";
	private static final Locale LOCALE = Locale.US;

	public static Calendar calendarFromDateTime(int dayOfWeek, int hourOfDay, int minute) {
		Calendar calendar = Calendar.getInstance();

		while (calendar.get(Calendar.DAY_OF_WEEK) != dayOfWeek) {
			calendar.add(Calendar.DATE, 1);
		}

		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(Calendar.MINUTE, minute);

		return calendar;
	}

	public static Calendar calendarFromDateTime(String dateTime) {
		Calendar calendar = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT, LOCALE);
		try {
			calendar.setTime(sdf.parse(dateTime));
		} catch (ParseException e) {
			throw new RuntimeException("Date or time parse exception: " + e);
		}

		return calendar;
	}

	public static String calendarToInnerString(Calendar c) {

		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		return String.format(DATE_TIME_TEMPLATE, dayOfMonth, month +1, year, hour, minute);
	}

	public static String calendarToUserString(Calendar c) {
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		return toUserString(dayOfWeek, hour, minute);
	}

	private static String toUserString(int dayOfWeek, int hour, int minute) {
		DateFormatSymbols symbols = new DateFormatSymbols(LOCALE);
		String weekday = symbols.getWeekdays()[dayOfWeek];
		return String.format("%s %02d:%02d", weekday, hour, minute);
	}
}
