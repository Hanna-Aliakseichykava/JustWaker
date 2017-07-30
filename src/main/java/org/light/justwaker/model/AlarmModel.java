package org.light.justwaker.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.light.justwaker.utility.DateTimeUtility;

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

	/*public AlarmModel(int id, List<Integer> daysOfWeek, List<Calendar> calendars, String label, String phrase, boolean isWeekly,
					  List<String> datesToIgnore) {
		this.id = id;
		this.daysOfWeek = new ArrayList<Integer>(daysOfWeek);
		this.datesToAlarm = DateTimeUtility.calendarsToInnerStrings(calendars);
		this.label = label;
		this.phrase = phrase;
		this.isWeekly = isWeekly;

		this.datesToIgnore.clear();
		this.datesToIgnore.addAll(datesToIgnore);
	}*/

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

	/*public List<String> getDatesToAlarm() {
            return datesToAlarm;
        }
        public void setDatesToAlarm(List<String> datesToAlarm) {
            this.datesToAlarm = datesToAlarm;
        }*/

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

	/*public void setDatesToAlarm(List<String> datesToAlarm) {
		this.datesToAlarm = datesToAlarm;
	}*/

	@Override
	public String toString() {
		String name = DayOfWeek.getShortCodesByNumbers(daysOfWeek) + " " + label;
		if(isWeekly) {
			name += " weekly";
		}

		return name;
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
}
