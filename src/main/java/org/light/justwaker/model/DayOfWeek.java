package org.light.justwaker.model;

import android.content.res.Resources;

import org.light.justwaker.utility.ResourcesUtility;

import java.util.List;

public enum DayOfWeek {

	MONDAY(2, 0),
	TUESDAY(3, 1),
	WEDNESDAY(4, 2),
	THURSDAY(5, 3),
	FRIDAY(6, 4),
	SATURDAY(7, 5),
	SUNDAY(1, 6);

	private int number;
	private int index;

	private DayOfWeek(int number, int index) {
		this.number = number;
		this.index = index;
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

	public static String getShortCodesByNumbers(Resources resources, List<Integer> numbers) {
		List<String> codes = ResourcesUtility.getDaysShortCodes(resources);
		String label = "";
		for(int number : numbers) {
			label += " " + codes.get(DayOfWeek.getIndexByNumber(number));
		}
		return label;
	}

}
