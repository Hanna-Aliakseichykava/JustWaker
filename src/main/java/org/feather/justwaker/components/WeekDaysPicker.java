package org.feather.justwaker.components;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import org.feather.justwaker.R;
import org.feather.justwaker.model.DayOfWeek;
import org.feather.justwaker.utility.ResourcesUtility;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 * Created by ANNA on 15.07.2017.
 */

public class WeekDaysPicker {

	private List<ToggleButton> buttons = new ArrayList<ToggleButton>();

	private WeekDaysPicker() {}

	private WeekDaysPicker(List<ToggleButton> buttons) {
		this();
		this.buttons = buttons;
	}

	public static WeekDaysPicker build(Activity activity, ViewGroup weekDaysSelectionWrapper) {
		List<String> days = ResourcesUtility.getDaysShortCodes(activity.getResources());

		int counter = 0;
		List<ToggleButton> _buttons = new ArrayList<ToggleButton>();
		for(String day : days) {
			ToggleButton btn = createButton(activity, weekDaysSelectionWrapper);
			btn.setId(btn.getId() + (++counter));
			btn.setText(day);
			btn.setTextOff(day);
			btn.setTextOn(day);

			_buttons.add(btn);
		}

		return new WeekDaysPicker(_buttons);
	}

	private static ToggleButton createButton(Activity activity, ViewGroup weekDaysSelectionWrapper) {
		View parentView = (View)activity.getLayoutInflater().inflate(R.layout.custom_toggle_button, weekDaysSelectionWrapper);
 		ToggleButton btn = (ToggleButton)parentView.findViewById(R.id.customToggableButton);
		return btn;
	}

	public List<Integer> getSelectedDayNumbers() {
		List<Integer> numbers = new ArrayList<Integer>();
		for(int i = 0; i < buttons.size(); i++) {
			ToggleButton btn = buttons.get(i);
			if(btn.isChecked()) {
				numbers.add(DayOfWeek.getNumberByIndex(i));
 			}
		}
		return numbers;
	}

	public void setSelectedWeekDays(List<Integer> numbers) {
		for(int i = 0; i < numbers.size(); i++) {
			int index = DayOfWeek.getIndexByNumber(numbers.get(i));
			ToggleButton btn = buttons.get(index);
			btn.setChecked(true);
		}
	}
}
