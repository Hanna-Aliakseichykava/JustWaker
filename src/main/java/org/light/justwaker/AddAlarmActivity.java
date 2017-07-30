package org.light.justwaker;

import org.light.justwaker.model.AlarmModel;
import org.light.justwaker.model.DayOfWeek;
import org.light.justwaker.utility.AlarmUtils;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.CheckBox;

import java.util.Arrays;
import java.util.Calendar;

public class AddAlarmActivity extends BaseAlarmViewActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_alarm);

		initControls();

		setSelectedWeekDays(Arrays.asList(new Integer [] {Calendar.getInstance().get(Calendar.DAY_OF_WEEK)}));
	}

	// Button listeners
	public void setAlarm(View view) {
		Context context = this.getApplicationContext();

		AlarmModel alarm = buildAlarm();

		/*boolean isWeekly = ((CheckBox)findViewById(R.id.checkboxWeeklyAlarm)).isChecked();
		AlarmUtils.addAlarm(context, getDateTime(),
				labelEdit.getText().toString(), phraseEdit.getText().toString(), isWeekly,
				datesToIgnore);*/

		AlarmUtils.addAlarm(context, alarm);

		goBack();
	}

	public void onClickOpenCalendarButton(View view) {
		Intent intent = new Intent(AddAlarmActivity.this, CalendarActivity.class);
		openCalendarView(intent);
	}
}
