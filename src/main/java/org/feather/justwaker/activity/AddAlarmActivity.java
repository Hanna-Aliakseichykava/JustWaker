package org.feather.justwaker.activity;

import org.feather.justwaker.R;
import org.feather.justwaker.model.AlarmModel;
import org.feather.justwaker.utility.AlarmUtils;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.View;

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

	public void setAlarm(View view) {
		Context context = this.getApplicationContext();

		AlarmModel alarm = buildAlarm();

		AlarmUtils.addAlarm(context, alarm);

		goBack();
	}

	public void onClickOpenCalendarButton(View view) {
		Intent intent = new Intent(AddAlarmActivity.this, CalendarActivity.class);
		openCalendarView(intent);
	}
}
