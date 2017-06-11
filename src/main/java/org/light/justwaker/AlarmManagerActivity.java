package org.light.justwaker;

import org.light.justwaker.R;
import org.light.justwaker.utility.AlarmUtils;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.CheckBox;

public class AlarmManagerActivity extends BaseAlarmViewActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_manager);

		labelEdit = (EditText)findViewById(R.id.in_label);
		phraseEdit = (EditText)findViewById(R.id.in_phrase);

		spinSelectedDay = (Spinner)findViewById(R.id.spinSelectedDay);
		tpSelectedTime = (TimePicker)findViewById(R.id.tpSelectedTime);
		initDateTimeControls();
	}

	// Button listeners
	public void setTimer(View view) {
		Context context = this.getApplicationContext();
		boolean isWeekly = ((CheckBox)findViewById(R.id.checkboxWeeklyAlarm)).isChecked();
		AlarmUtils.addAlarm(context, getDateTime(), labelEdit.getText().toString(), phraseEdit.getText().toString(), isWeekly);
		goBack();
	}

	public void onClickOpenCalendarButton(View view) {
		Intent intent = new Intent(AlarmManagerActivity.this, CalendarActivity.class);
		startActivity(intent);
	}
}
