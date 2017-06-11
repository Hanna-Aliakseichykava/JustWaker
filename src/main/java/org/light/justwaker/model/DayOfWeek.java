package org.light.justwaker.model;

public enum DayOfWeek {

	MONDAY(2,"Monday"),
	TUESDAY(3, "Tuesday"),
	WEDNESDAY(4, "Wednesday"),
	THURSDAY(5, "Thursday"),
	FRIDAY(6, "Friday"),
	SATURDAY(7, "Saturday"),
	SUNDAY(1,"Sunday");

	private int number;
	private String text;

	private DayOfWeek(int number, String text) {
		this.number = number;
		this.text = text;
	}

	public static DayOfWeek getByText(String dayAsText) {
		for(DayOfWeek day : values()) {
			if(day.getText().equalsIgnoreCase(dayAsText)) {
				return day;
			}
		}
		throw new RuntimeException("Day [" + dayAsText + "] is not found");
	}

	public int getNumber() {
		return number;
	}

	public String getText() {
		return text;
	}
}
