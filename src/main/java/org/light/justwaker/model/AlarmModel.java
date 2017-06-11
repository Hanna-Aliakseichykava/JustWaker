package org.light.justwaker.model;

import java.util.Calendar;

import org.light.justwaker.utility.DateTimeUtility;

public class AlarmModel implements Comparable<AlarmModel> {

	//TDOD:? очистить и восстановить после перезагрузки

	private int id;

	private String dateTime;

	private boolean isWeekly;

	private String label;

	private String phrase;

	public AlarmModel() {
	}

	public AlarmModel(int id, Calendar calendar, String label, String phrase, boolean isWeekly) {
		this.id = id;
		this.dateTime = DateTimeUtility.calendarToInnerString(calendar);
		this.label = label;
		this.phrase = phrase;
		this.isWeekly = isWeekly;
	}

	public int getId() {
		return id;
	}

	public String getDateTime() {
		return dateTime;
	}

	public String getLabel() {
		return label;
	}

	public boolean isWeekly() {
		return isWeekly;
	}

	public Calendar getCalendar() {
		return DateTimeUtility.calendarFromDateTime(dateTime);
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public void setDateTime(Calendar calendar) {
		this.dateTime = DateTimeUtility.calendarToInnerString(calendar);
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

	@Override
	public String toString() {
		String name = dateTime + " " + label;
		if(isWeekly) {
			name += " weekly";
		}
		return name;
	}

	@Override
	public int compareTo(AlarmModel other) {
		int result = this.getCalendar().compareTo(other.getCalendar());
		if(result != 0) {
			return result;
		} else {
			return this.label.compareTo(other.getLabel());
		}
	}
}
