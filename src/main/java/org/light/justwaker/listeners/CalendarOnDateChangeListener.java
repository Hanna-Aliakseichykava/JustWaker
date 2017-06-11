package org.light.justwaker.listeners;

import android.content.Context;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;

public class CalendarOnDateChangeListener implements OnDateChangeListener {

	Context context;

	public CalendarOnDateChangeListener(Context context) {
		this.context = context;
	}

	@Override
	public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
		int mYear = year;
		int mMonth = month;
		int mDay = dayOfMonth;
		String selectedDate = new StringBuilder().append(mMonth + 1)
				.append("-").append(mDay).append("-").append(mYear)
				.append(" ").toString();
		Toast.makeText(context, selectedDate, Toast.LENGTH_LONG).show();
		
		
	}

}
