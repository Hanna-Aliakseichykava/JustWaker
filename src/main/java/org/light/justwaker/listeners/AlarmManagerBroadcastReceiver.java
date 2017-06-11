package org.light.justwaker.listeners;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.light.justwaker.AlarmsListActivity;
import org.light.justwaker.EditAlarmActivity;
import org.light.justwaker.WakeUpActivity;
import org.light.justwaker.model.AlarmModel;
import org.light.justwaker.utility.AlarmUtils;
import org.light.justwaker.utility.SpeechUtility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

	private static final String TAG = "AlarmsReceiver:";

	@Override
	public void onReceive(Context context, Intent intent) {
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
		// Осуществляем блокировку
		wl.acquire();

		Bundle extras = intent.getExtras();
		if (extras != null && extras.getString(AlarmUtils.ALARM_PHRASE) != null) {
			String phrase = extras.getString(AlarmUtils.ALARM_PHRASE) + " ";
			Log.i(TAG, "Alarm Phrase: " + phrase);
			openWakeUpWindow(context, phrase);
		}

		// Разблокируем поток.
		wl.release();
	}

	public void openWakeUpWindow(Context context, String phrase) {
		Intent intent = new Intent(context, WakeUpActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		Bundle bundle = new Bundle();
		bundle.putString(AlarmUtils.ALARM_PHRASE, phrase);
		intent.putExtras(bundle);

		context.startActivity(intent);
	}

}
