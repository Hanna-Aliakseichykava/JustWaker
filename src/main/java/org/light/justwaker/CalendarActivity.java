package org.light.justwaker;

import org.light.justwaker.listeners.CalendarOnDateChangeListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CalendarView;

public class CalendarActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.selection_calendar);

		CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView1);
		calendarView.setOnDateChangeListener(new CalendarOnDateChangeListener(this));
	}

	protected void goBack() {
		super.onBackPressed();
	}
}
