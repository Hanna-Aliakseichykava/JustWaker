package org.light.justwaker.utility;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

import java.util.Date;

public class DateTimeUtility {

	private static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";
	private static final String DATE_TIME_TEMPLATE = "%02d-%02d-%4d %02d:%02d";
	private static final Locale LOCALE = Locale.ENGLISH;

	private static final String DATE_FORMAT = "dd-MM-yyyy";

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
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT, LOCALE);

		Calendar calendar = Calendar.getInstance();

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

	/* Dates */

	public static Calendar dateFromString(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(dateStr));
			return calendar;
		} catch (ParseException e) {
			throw new RuntimeException("Date parse exception: " + e);
		}
	}

	public static String dateToInnerString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(date);
	}

	public static ArrayList<String> datesToInnerStrings(List<Date> dates) {
		ArrayList<String> target = new ArrayList<String>();

		for(Date date : dates) {
			target.add(dateToInnerString(date));
		}
		return target;
	}

	public static List<Date> datesFromStrings(List<String> strDates) {
		List<Date> target = new ArrayList<Date>();

		for(String strDate : strDates) {
			target.add(dateFromString(strDate).getTime());
		}
		return target;
	}

	private static List<Calendar> calendarsFromStrings(List<String> strDates) {
		List<Calendar> target = new ArrayList<Calendar>();

		for(String strDate : strDates) {
			target.add(dateFromString(strDate));
		}
		return target;
	}

	private static boolean containsDay(List<Calendar> dates, Calendar date) {
		for(Calendar calendar : dates) {
			if(date.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
					&& date.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
					&& date.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)) {
				return true;
			}
		}
		return false;
	}

	public static boolean containsToday(List<String> strDates) {
		List<Calendar> dates = DateTimeUtility.calendarsFromStrings(strDates);
		Calendar today = Calendar.getInstance();
		return containsDay(dates, today);
	}
}
