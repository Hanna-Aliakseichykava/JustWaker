package org.feather.justwaker.listeners;

import java.util.ArrayList;

import org.feather.justwaker.activity.AddAlarmActivity;
import org.feather.justwaker.activity.WakeUpActivity;
import org.feather.justwaker.utility.AlarmUtils;
import org.feather.justwaker.utility.DateTimeUtility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

	private static final String TAG = "AlarmsReceiver:";

	@Override
	public void onReceive(Context context, Intent intent) {
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");

		wl.acquire();

		Bundle extras = intent.getExtras();
		if (extras != null && extras.getString(AlarmUtils.ALARM_PHRASE) != null) {
			String label = extras.getString(AlarmUtils.ALARM_LABEL);
			String phrase = extras.getString(AlarmUtils.ALARM_PHRASE);
			ArrayList<String> datesToDeselect = intent.getStringArrayListExtra(AddAlarmActivity.SELECTED_DATES_PARAMETER);
			Log.i(TAG, "Alarm: " + label + ", EXCLUDED: " + datesToDeselect);

			if(DateTimeUtility.containsToday(datesToDeselect)) {
				Log.i(TAG, "Skip wake up");
			} else {
				Log.i(TAG, "Open Wake Up Window");
				openWakeUpWindow(context, label, phrase);
			}

		}


		wl.release();
	}

	private void openWakeUpWindow(Context context, String label, String phrase) {
		Intent intent = new Intent(context, WakeUpActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		Bundle bundle = new Bundle();
		bundle.putString(AlarmUtils.ALARM_LABEL, label);
		intent.putExtra(AlarmUtils.ALARM_PHRASE, phrase);
		intent.putExtras(bundle);

		context.startActivity(intent);
	}

}
