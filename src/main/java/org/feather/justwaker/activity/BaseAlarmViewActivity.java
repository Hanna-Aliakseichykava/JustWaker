package org.feather.justwaker.activity;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;

import org.feather.justwaker.R;
import org.feather.justwaker.model.AlarmModel;
import org.feather.justwaker.utility.DateTimeUtility;
import org.feather.justwaker.components.WeekDaysPicker;
import org.feather.justwaker.utility.SpeechUtility;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.text.TextUtils;

public class BaseAlarmViewActivity extends BaseMenuActivity {

	TimePicker tpSelectedTime;

	WeekDaysPicker daysPicker;

	protected EditText labelEdit;
	protected EditText phraseEdit;

	protected ArrayList<String> datesToIgnore = new ArrayList<String>();
	protected TextView datesToIgnoreLabel;

	protected void initControls() {
		labelEdit = (EditText)findViewById(R.id.in_label);
		phraseEdit = (EditText)findViewById(R.id.in_phrase);

		tpSelectedTime = (TimePicker)findViewById(R.id.tpSelectTime);

		initWeekDaysSelectionControl();
	}

	private void initWeekDaysSelectionControl() {

		ViewGroup weekDaysSelectionWrapper = (ViewGroup) findViewById(R.id.weekDaysSelectionWrapper);

		daysPicker = WeekDaysPicker.build(this, weekDaysSelectionWrapper);

		datesToIgnoreLabel = (TextView)findViewById(R.id.deselected_days);
	}

	protected void setSelectedWeekDays(List<Integer> numbers) {
		daysPicker.setSelectedWeekDays(numbers);
	}

	public void testVoice(View v) {
		String toSpeak = phraseEdit.getText().toString();
		Context context = this.getApplicationContext();
		Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
		SpeechUtility.speak(context, toSpeak);
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

			datesToIgnore = selectedDates;
			datesToIgnoreLabel.setText(TextUtils.join(", ", datesToIgnore));
		}
	}

	protected void openCalendarView(Intent intent) {
		intent.putStringArrayListExtra(SELECTED_DATES_PARAMETER,
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
