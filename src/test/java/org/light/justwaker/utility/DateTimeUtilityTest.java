package org.light.justwaker.utility;

import org.junit.Test;

import java.util.Calendar;
import static org.junit.Assert.*;

public class DateTimeUtilityTest {

	/*@Test
	public void testCalendarFromDateAndTime() {
		Calendar calendar = DateTimeUtility.calendarFromDateTime(1, 18, 35);

		assertEquals(calendar.get(Calendar.DAY_OF_WEEK), 1);

		assertEquals(calendar.get(Calendar.HOUR_OF_DAY), 18);
		assertEquals(calendar.get(Calendar.MINUTE), 35);

		//verify date, month and year
		Calendar currentCalendar = Calendar.getInstance();
		assertTrue(calendar.compareTo(currentCalendar) > 0);

		assertEquals(DateTimeUtility.calendarToUserString(calendar), "Sunday 18:35");
	}

	@Test
	public void testCalendarFromDateAndTime_2() {
		Calendar calendar = DateTimeUtility.calendarFromDateTime(2, 8, 1);

		assertEquals(calendar.get(Calendar.DAY_OF_WEEK), 2);

		assertEquals(calendar.get(Calendar.HOUR_OF_DAY), 8);
		assertEquals(calendar.get(Calendar.MINUTE), 1);

		//verify date, month and year
		Calendar currentCalendar = Calendar.getInstance();
		if (currentCalendar.get(Calendar.DAY_OF_WEEK) != calendar.get(Calendar.DAY_OF_WEEK)) {
			assertTrue(calendar.compareTo(currentCalendar) >= 0);
		} else {
			assertTrue(calendar.compareTo(currentCalendar) <= 0);
		}


		assertEquals(DateTimeUtility.calendarToUserString(calendar), "Monday 08:01");
	}*/

	@Test
	public void testCalendarFromDateAndTime_3() {
		Calendar calendar = DateTimeUtility.calendarFromDateTime("24-12-2017 18:35");

		assertEquals(calendar.get(Calendar.YEAR), 2017);
		assertEquals(calendar.get(Calendar.MONTH), 12 - 1);
		assertEquals(calendar.get(Calendar.DAY_OF_MONTH), 24);

		assertEquals(calendar.get(Calendar.HOUR_OF_DAY), 18);
		assertEquals(calendar.get(Calendar.MINUTE), 35);


		assertEquals(DateTimeUtility.calendarToInnerString(calendar), "24-12-2017 18:35");
	}

	@Test
	public void testCalendarFromDateAndTime_4() {
		Calendar calendar = DateTimeUtility.calendarFromDateTime("02-03-2017 08:01");

		assertEquals(calendar.get(Calendar.YEAR), 2017);
		assertEquals(calendar.get(Calendar.MONTH), 3 - 1);
		assertEquals(calendar.get(Calendar.DAY_OF_MONTH), 2);

		assertEquals(calendar.get(Calendar.HOUR_OF_DAY), 8);
		assertEquals(calendar.get(Calendar.MINUTE), 1);


		assertEquals(DateTimeUtility.calendarToInnerString(calendar), "02-03-2017 08:01");
	}

	@Test
	public void testDateFromString() {
		Calendar calendar = DateTimeUtility.dateFromString("02-03-2017");

		assertEquals(calendar.get(Calendar.YEAR), 2017);
		assertEquals(calendar.get(Calendar.MONTH), 3 - 1);
		assertEquals(calendar.get(Calendar.DAY_OF_MONTH), 2);

		assertEquals(DateTimeUtility.dateToInnerString(calendar.getTime()), "02-03-2017");
	}

	@Test
	public void testDateFromString_2() {
		Calendar calendar = DateTimeUtility.dateFromString("12-11-2017");

		assertEquals(calendar.get(Calendar.YEAR), 2017);
		assertEquals(calendar.get(Calendar.MONTH), 11 - 1);
		assertEquals(calendar.get(Calendar.DAY_OF_MONTH), 12);

		assertEquals(DateTimeUtility.dateToInnerString(calendar.getTime()), "12-11-2017");
	}
}
