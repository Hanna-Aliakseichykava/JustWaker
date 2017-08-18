package org.feather.justwaker.model;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.feather.justwaker.utility.DateTimeUtility;

public class AlarmModel implements Comparable<AlarmModel> {

	private int id;

	private List<Integer> daysOfWeek;
	private List<String> datesToAlarm;

	private boolean isWeekly;

	private String label;

	private String phrase;

	private List<String> datesToIgnore = new ArrayList<String>();

	public AlarmModel() {
	}

	public int getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public boolean isWeekly() {
		return isWeekly;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setWeekly(boolean isWeekly) {
		this.isWeekly = isWeekly;
	}

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	public List<String> getDatesToIgnore() {
		return datesToIgnore;
	}

	public void setDatesToIgnore(List<String> datesToIgnore) {
		this.datesToIgnore = datesToIgnore;
	}


	public Calendar getOneCalendar() {
		return DateTimeUtility.calendarFromDateTime(datesToAlarm.get(0));
	}

	public List<Calendar> getCalendars() {
		return DateTimeUtility.calendarsFromDateTime(datesToAlarm);
	}

	public void setDatesToAlarm(List<Calendar> calendars) {
		setRawDatesToAlarm(DateTimeUtility.calendarsToInnerStrings(calendars));
	}

	public void setRawDatesToAlarm(List<String> dates) {
		this.datesToAlarm = dates;
	}

	public List<Integer> getDaysOfWeek() {
		return daysOfWeek;
	}

	public void setDaysOfWeek(List<Integer> daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}

	public List<String> getDatesToAlarm() {
		return datesToAlarm;
	}

	@Override
	public int compareTo(AlarmModel other) {
		int result = this.getLabel().compareTo(other.getLabel());
		if(result != 0) {
			return result;
		} else {
			return this.label.compareTo(other.getLabel());
		}
	}

	public String getInfo(Resources resources) {
		String date = DateTimeUtility.calendarToUserString(datesToAlarm.get(0));
		String days = DayOfWeek.getShortCodesByNumbers(resources, daysOfWeek);
		String name = date + " " + days + " " + label;
		if(isWeekly) {
			name += " weekly";
		}

		return name;
	}
}
