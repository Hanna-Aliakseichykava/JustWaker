package org.light.justwaker;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;

import org.light.justwaker.model.AlarmModel;
import org.light.justwaker.model.DayOfWeek;
import org.light.justwaker.utility.DateTimeUtility;
import org.light.justwaker.components.WeekDaysPicker;
import org.light.justwaker.utility.SpeechUtility;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

public class BaseAlarmViewActivity extends BaseMenuActivity {

	TimePicker tpSelectedTime;

	WeekDaysPicker daysPicker;

	protected EditText labelEdit;
	protected EditText phraseEdit;

	protected ArrayList<String> datesToIgnore = new ArrayList<String>();

	protected void initControls() {
		labelEdit = (EditText)findViewById(R.id.in_label);
		phraseEdit = (EditText)findViewById(R.id.in_phrase);

		tpSelectedTime = (TimePicker)findViewById(R.id.tpSelectedTime);

		initWeekDaysSelectionControl();
	}

	private void initWeekDaysSelectionControl() {

		ViewGroup weekDaysSelectionWrapper = (ViewGroup) findViewById(R.id.weekDaysSelectionWrapper);

		daysPicker = WeekDaysPicker.build(this, weekDaysSelectionWrapper);
	}

	protected void setSelectedWeekDays(List<Integer> numbers) {
		daysPicker.setSelectedWeekDays(numbers);
	}

	public void testVoice(View v) {
		String toSpeak = phraseEdit.getText().toString();
		Context context = this.getApplicationContext();
		Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
		SpeechUtility.speak(context, toSpeak);

		Toast.makeText(this, "!!! Days " + daysPicker.getSelectedDayNumbers(), Toast.LENGTH_LONG).show();
	}

	protected List<Calendar> getDateTime() {
		return DateTimeUtility.calendarsFromDateTime(daysPicker.getSelectedDayNumbers(), tpSelectedTime.getCurrentHour(), tpSelectedTime.getCurrentMinute());
	}

	protected void goBack() {
		super.onBackPressed();
	}

	private static final int CALENDAR_CHILD_ACTIVITY_REQUEST_CODE = 3;
	public static final String SELECTED_DATES_PARAMETER = "SELECTED_DATES_PARAMETER";

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CALENDAR_CHILD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
			ArrayList<String> selectedDates = data.getStringArrayListExtra(SELECTED_DATES_PARAMETER);

			Toast.makeText(this, "!!! " + selectedDates.toString(), Toast.LENGTH_LONG).show();
			datesToIgnore = selectedDates;
		}
	}

	protected void openCalendarView(Intent intent) {
		intent.putStringArrayListExtra(AddAlarmActivity.SELECTED_DATES_PARAMETER,
				datesToIgnore);
		startActivityForResult(intent, CALENDAR_CHILD_ACTIVITY_REQUEST_CODE);
	}

	protected AlarmModel buildAlarm() {

		AlarmModel alarm = new AlarmModel();
		fillAlarmInfo(alarm);

		return alarm;
	}

	protected void fillAlarmInfo(AlarmModel alarm) {
		boolean isWeekly = ((CheckBox)findViewById(R.id.checkboxWeeklyAlarm)).isChecked();

		alarm.setDaysOfWeek(daysPicker.getSelectedDayNumbers());
		alarm.setDatesToAlarm(getDateTime());

		alarm.setLabel(labelEdit.getText().toString());
		alarm.setPhrase(phraseEdit.getText().toString());
		alarm.setWeekly(isWeekly);
		alarm.setDatesToIgnore(datesToIgnore);
	}
}
