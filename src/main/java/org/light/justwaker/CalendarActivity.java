package org.light.justwaker;

import org.light.justwaker.utility.DateTimeUtility;

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

	private CalendarPickerView calendar = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.multi_selection_calendar_view);

		Intent intent = this.getIntent();

		ArrayList<String> selectedDates = intent.getStringArrayListExtra(AlarmManagerActivity.SELECTED_DATES_PARAMETER);

		Toast.makeText(this, "%%% " + selectedDates.toString(), Toast.LENGTH_LONG).show();

		Calendar nextYear = Calendar.getInstance();
		nextYear.add(Calendar.YEAR, 1);

		Date today = new Date();

		calendar = (CalendarPickerView) findViewById(R.id.calendar_view);

		calendar.init(today, nextYear.getTime())
				.withSelectedDates(DateTimeUtility.datesFromStrings(selectedDates))
				.inMode(SelectionMode.MULTIPLE);
	}

	public void onCloseCalendar(View view) {
		Toast.makeText(this, calendar.getSelectedDates().toString(), Toast.LENGTH_LONG).show();

		Intent output = new Intent();
		output.putStringArrayListExtra(AlarmManagerActivity.SELECTED_DATES_PARAMETER,
				DateTimeUtility.datesToInnerStrings(calendar.getSelectedDates()));

		setResult(RESULT_OK, output);
		finish();
	}
}
