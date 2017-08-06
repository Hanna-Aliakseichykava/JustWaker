package org.light.justwaker;

import java.util.Calendar;

import org.light.justwaker.model.AlarmModel;
import org.light.justwaker.utility.AlarmUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;
import java.util.ArrayList;

public class EditAlarmActivity extends BaseAlarmViewActivity {

	private static final String TAG = EditAlarmActivity.class.getSimpleName();

	public static final String ALARM_ID = "ALARM_ID";

	private AlarmModel alarm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_alarm);

		initControls();

		fillOldValues(this.getIntent());
	}

	private void fillOldValues(Intent intent) {
		Bundle extras = intent.getExtras();
		CheckBox isWeeklyCheckbox = (CheckBox)findViewById(R.id.checkboxWeeklyAlarm);

		if (extras != null && extras.containsKey(ALARM_ID)) {

			int alarmId = extras.getInt(ALARM_ID);
			alarm = AlarmUtils.getAlarmById(this, alarmId);
			if(alarm != null) {

				labelEdit.setText(alarm.getLabel());
				phraseEdit.setText(alarm.getPhrase());
				isWeeklyCheckbox.setChecked(alarm.isWeekly());

				Calendar calendar = alarm.getOneCalendar();
				int hour = calendar.get(Calendar.HOUR_OF_DAY);
				int minute = calendar.get(Calendar.MINUTE);

				tpSelectedTime.setCurrentHour(hour);
				tpSelectedTime.setCurrentMinute(minute);

				setSelectedWeekDays(alarm.getDaysOfWeek());

				datesToIgnore = (ArrayList)alarm.getDatesToIgnore();
			} else {
				Log.e(TAG, "Alarm is not found by id: " + alarmId);
				Toast.makeText(this, "Alarm is not found by id: " + alarmId, Toast.LENGTH_SHORT).show();
				goBack();
			}

		} else {
			Log.e(TAG, "Alarm id is not found");
			Toast.makeText(this, "Alarm is not found", Toast.LENGTH_SHORT).show();
			goBack();
		}
	}

	// Button listeners
	public void updateAlarm(View view) {
		Context context = this.getApplicationContext();

		fillAlarmInfo(alarm);

		AlarmUtils.updateAlarm(context, alarm);
		goBack();
	}

	public void onClickOpenCalendarButton(View view) {
		Intent intent = new Intent(EditAlarmActivity.this, CalendarActivity.class);
		openCalendarView(intent);
	}
}
