package org.light.justwaker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.light.justwaker.model.DayOfWeek;
import org.light.justwaker.utility.DateTimeUtility;
import org.light.justwaker.utility.SpeechUtility;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class BaseAlarmViewActivity extends BaseMenuActivity {

	TimePicker tpSelectedTime;

	Spinner spinSelectedDay;

	protected EditText labelEdit;
	protected EditText phraseEdit;

	protected ArrayList<String> datesToIgnore = new ArrayList<String>();

	public void initDateTimeControls() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_day_picker, getResources().getStringArray(R.array.days_of_week));
		spinSelectedDay.setAdapter(adapter); 
	}

	public void testVoice(View v) {
		Context context = this.getApplicationContext();
		String toSpeak = phraseEdit.getText().toString();
		Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
		SpeechUtility.speak(context, toSpeak);
	}

	protected Calendar getDateTime() {
		String strDay = ((String)spinSelectedDay.getSelectedItem());
		return DateTimeUtility.calendarFromDateTime(DayOfWeek.getByText(strDay).getNumber(), tpSelectedTime.getCurrentHour(), tpSelectedTime.getCurrentMinute());
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
		intent.putStringArrayListExtra(AlarmManagerActivity.SELECTED_DATES_PARAMETER,
				datesToIgnore);
		startActivityForResult(intent, CALENDAR_CHILD_ACTIVITY_REQUEST_CODE);
	}
}
