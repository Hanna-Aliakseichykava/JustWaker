package org.feather.justwaker.activity;

import org.feather.justwaker.R;
import org.feather.justwaker.utility.DateTimeUtility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView.SelectionMode;

public class CalendarActivity extends Activity {

	public static final String CALENDAR_ACTIVITY = "CALENDAR_ACTIVITY";

	private CalendarPickerView calendar = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.multi_selection_calendar_view);

		Intent intent = this.getIntent();

		ArrayList<String> selectedDates = intent.getStringArrayListExtra(AddAlarmActivity.SELECTED_DATES_PARAMETER);

		Log.i(CALENDAR_ACTIVITY, "SELECTED: " + selectedDates.toString());

		Calendar nextYear = Calendar.getInstance();
		nextYear.add(Calendar.YEAR, 1);

		Date today = new Date();

		calendar = (CalendarPickerView) findViewById(R.id.calendar_view);

		calendar.init(today, nextYear.getTime())
			.inMode(SelectionMode.MULTIPLE)
			.withSelectedDates(DateTimeUtility.datesFromStrings(selectedDates));
	}

	public void onCloseCalendar(View view) {
		Intent output = new Intent();
		output.putStringArrayListExtra(AddAlarmActivity.SELECTED_DATES_PARAMETER,
			DateTimeUtility.datesToInnerStrings(calendar.getSelectedDates()));

		setResult(RESULT_OK, output);
		finish();
	}
}
