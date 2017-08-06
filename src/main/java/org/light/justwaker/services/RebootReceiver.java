package org.light.justwaker.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.light.justwaker.model.AlarmModel;
import org.light.justwaker.utility.AlarmUtils;

import java.util.List;

/**
 * Created by ANNA on 06.08.2017.
 */

public class RebootReceiver extends BroadcastReceiver {

	private static final String TAG = "RebootReceiver:";

	public void onReceive(Context context, Intent arg1) {
		Log.i(TAG, "Phone is rebooted, alarms are re-created...");

		List<AlarmModel> alarms = AlarmUtils.getAlarms(context);

		AlarmUtils.cancelAllAlarms(context);

		for(AlarmModel alarm : alarms) {
			AlarmUtils.addAlarm(context, alarm);
		}
	}
}
