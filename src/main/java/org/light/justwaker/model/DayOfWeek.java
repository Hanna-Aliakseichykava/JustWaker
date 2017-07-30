package org.light.justwaker.model;

public enum DayOfWeek {

	MONDAY(2, 0, "Monday"),
	TUESDAY(3, 1, "Tuesday"),
	WEDNESDAY(4, 2, "Wednesday"),
	THURSDAY(5, 3, "Thursday"),
	FRIDAY(6, 4, "Friday"),
	SATURDAY(7, 5, "Saturday"),
	SUNDAY(1, 6, "Sunday");

	private int number;
	private int index;
	private String text;

	private DayOfWeek(int number, int index, String text) {
		this.number = number;
		this.index = index;
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

	public static int getIndexByNumber(int number) {
		for(DayOfWeek day : values()) {
			if(day.getNumber() == number) {
				return day.getIndex();
			}
		}
		throw new RuntimeException("Day [" + number + "] is not found");
	}

	public static int getNumberByIndex(int index) {
		for(DayOfWeek day : values()) {
			if(day.getIndex() == index) {
				return day.getNumber();
			}
		}
		throw new RuntimeException("Index [" + index + "] is not found");
	}

	public int getNumber() {
		return number;
	}

	public int getIndex() { return index;}

	public String getText() {
		return text;
	}

}
