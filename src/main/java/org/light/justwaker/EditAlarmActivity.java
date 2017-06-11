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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditAlarmActivity extends BaseAlarmViewActivity {

	private static final String TAG = EditAlarmActivity.class.getSimpleName();

	public static final String ALARM_ID = "ALARM_ID";

	private AlarmModel alarm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_alarm);

		labelEdit = (EditText)findViewById(R.id.in_correct_label);
		phraseEdit = (EditText)findViewById(R.id.in_correct_phrase);

		spinSelectedDay = (Spinner)findViewById(R.id.spinEditSelectedDay);
		tpSelectedTime = (TimePicker)findViewById(R.id.tpEditSelectedTime);

		initDateTimeControls();

		fillOldValues(this.getIntent());
	}

	private void fillOldValues(Intent intent) {
		Bundle extras = intent.getExtras();
		CheckBox isWeeklyCheckbox = (CheckBox)findViewById(R.id.checkboxEditWeeklyAlarm);

		if (extras != null && extras.containsKey(ALARM_ID)) {

			int alarmId = extras.getInt(ALARM_ID);
			alarm = AlarmUtils.getAlarmById(this, alarmId);
			if(alarm != null) {

				labelEdit.setText(alarm.getLabel());
				phraseEdit.setText(alarm.getPhrase());
				isWeeklyCheckbox.setChecked(alarm.isWeekly());

				Calendar calendar = alarm.getCalendar();
				int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
				int hour = calendar.get(Calendar.HOUR_OF_DAY);
				int minute = calendar.get(Calendar.MINUTE);

				spinSelectedDay.setSelection(dayOfWeek);
				tpSelectedTime.setCurrentHour(hour);
				tpSelectedTime.setCurrentMinute(minute);
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
		boolean isWeekly = ((CheckBox)findViewById(R.id.checkboxEditWeeklyAlarm)).isChecked();

		alarm.setDateTime(getDateTime());
		alarm.setLabel(labelEdit.getText().toString());
		alarm.setPhrase(phraseEdit.getText().toString());
		alarm.setWeekly(isWeekly);

		AlarmUtils.updateAlarm(context, alarm);
		goBack();
	}
}
