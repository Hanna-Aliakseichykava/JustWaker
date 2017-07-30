package org.light.justwaker.utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.light.justwaker.AddAlarmActivity;
import org.light.justwaker.listeners.AlarmManagerBroadcastReceiver;
import org.light.justwaker.model.AlarmModel;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class AlarmUtils {

	private static final String sTagAlarms = ":alarms";

	public static final String ALARM_PHRASE = "ALARM_PHRASE";

	public static void addAlarm(Context context, Calendar calendar, String label, String phrase,
								boolean isWeekly, List<String> datesToIgnore) {

		AlarmModel alarm = prepareAlarm(context, calendar, label, phrase, isWeekly, datesToIgnore);

		addSystemAlarm(context, alarm);
	}

	public static void updateAlarm(Context context, AlarmModel alarm) {

		cancelAlarm(context, alarm);

		saveAlarmInPreferences(context, alarm);
		addSystemAlarm(context, alarm);
	}

	private static AlarmModel prepareAlarm(Context context, Calendar calendar,
								String label, String phrase, boolean isWeekly,
								List<String> datesToIgnore) {

		List<AlarmModel> alarms = getAlarms(context);
		int notificationId = alarms.size() + 1;

		AlarmModel alarm = new AlarmModel(notificationId, calendar, label, phrase, isWeekly, datesToIgnore);
		saveAlarmInPreferences(context, alarm);

		return alarm;
	}

	private static void addSystemAlarm(Context context, AlarmModel alarm) {

		Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
		intent.putExtra(ALARM_PHRASE, alarm.getPhrase());
		intent.putStringArrayListExtra(AddAlarmActivity.SELECTED_DATES_PARAMETER,
				(ArrayList<String>) alarm.getDatesToIgnore());

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm.getId(), intent,
				PendingIntent.FLAG_CANCEL_CURRENT);

		if(alarm.isWeekly()) {
			//int weekInterval= 7 * 24 * 60 * 60 * 1000;
			long weekInterval= 7 * AlarmManager.INTERVAL_DAY;
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarm.getCalendar().getTimeInMillis(), weekInterval, pendingIntent);
		} else {
			alarmManager.set(AlarmManager.RTC_WAKEUP, alarm.getCalendar().getTimeInMillis(), pendingIntent);
		}
	}

	public static void cancelAlarm(Context context, AlarmModel alarm) {
		Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
		cancelAlarm(context, intent, alarm.getId());
	}

	private static void cancelAlarm(Context context, Intent intent, int notificationId) {
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent,
				PendingIntent.FLAG_CANCEL_CURRENT);
		alarmManager.cancel(pendingIntent);
		pendingIntent.cancel();

		removeAlarmByIdFromPreferences(context, notificationId);
	}

	public static void cancelAllAlarms(Context context) {
		Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
		for (AlarmModel alarm : getAlarms(context)) {
			cancelAlarm(context, intent, alarm.getId());
		}
	}

	/*public static boolean hasAlarm(Context context, Intent intent, int notificationId) {
		return PendingIntent.getBroadcast(context,
			notificationId, intent, PendingIntent.FLAG_NO_CREATE) != null;
	}*/

	private static void saveAlarmInPreferences(Context context, AlarmModel alarm) {
		List<AlarmModel> alarms = getAlarms(context);

		alarms.add(alarm);

		saveAlarmsInPreferences(context, alarms);
	}

	private static void removeAlarmByIdFromPreferences(Context context, int id) {
		List<AlarmModel> alarms = getAlarms(context);

		for (int i = 0; i < alarms.size(); i++) {
			if (alarms.get(i).getId() == id)
				alarms.remove(i);
		}

		saveAlarmsInPreferences(context, alarms);
	}

	/*public static List<String> getAlarmNames(Context context) {
		List<AlarmModel> alarms = getAlarms(context);

		List<String> alarmNames = new ArrayList<>();
		for(AlarmModel alarm: alarms) {
			alarmNames.add(alarm.toString());
		}
		return alarmNames;
	}*/

	public static AlarmModel getAlarmById(Context context, int id) {
		for (AlarmModel alarm : getAlarms(context)) {
			if(alarm.getId() == id) {
				return alarm;
			}
		}
		return null;
	}

	public static List<AlarmModel> getAlarms(Context context) {
		List<AlarmModel> alarms = new ArrayList<>();
		try {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			JSONArray jsonArray = new JSONArray(prefs.getString(context.getPackageName() + sTagAlarms, "[]"));

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				AlarmModel alarm = parseJsonObjectToAlarm(obj);
				alarms.add(alarm);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Retrieve alarms", "Parse error: " + e.getMessage());
		}

		return alarms;
	}

	private static void saveAlarmsInPreferences(Context context, List<AlarmModel> alarms) {
		JSONArray jsonArray = new JSONArray();
		for (AlarmModel alarm : alarms) {
			JSONObject jsonObj = convertToJsonObject(alarm);
			jsonArray.put(jsonObj);
		}

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(context.getPackageName() + sTagAlarms, jsonArray.toString());

		editor.apply();
	}

	protected static JSONObject convertToJsonObject(AlarmModel alarm) {
		JSONObject jo = new JSONObject();
		try {
			jo.put("id", alarm.getId());
			jo.put("date_time", alarm.getDateTime());
			jo.put("label", alarm.getLabel());
			jo.put("phrase", alarm.getPhrase());
			jo.put("is_weekly", alarm.isWeekly());

			JSONArray array = new JSONArray();
			for (String str : alarm.getDatesToIgnore()) {
				array.put(str);
			}
			jo.put("dates_to_ignore", array);

		} catch (JSONException e) {
			e.printStackTrace();
			Log.e("Convert Alarm to json", "Save error: " + e.getMessage());
		}

		return jo;
	}

	protected static AlarmModel parseJsonObjectToAlarm(JSONObject jo) {
		AlarmModel alarm = new AlarmModel();
		try {
			alarm.setId(jo.getInt("id"));
			alarm.setDateTime(jo.getString("date_time"));
			alarm.setLabel(jo.getString("label"));
			alarm.setPhrase(jo.getString("phrase"));
			alarm.setWeekly(jo.getBoolean("is_weekly"));

			JSONArray jsonArray = jo.getJSONArray("dates_to_ignore");
			List<String> datesToIgnore = new ArrayList<String>();
			for (int i = 0; i < jsonArray.length(); i++) {
				String str = jsonArray.getString(i);
				datesToIgnore.add(str);
			}
			alarm.setDatesToIgnore(datesToIgnore);

		} catch (JSONException e) {
			e.printStackTrace();
			Log.e("Convert Alarm to json", "Save error: " + e.getMessage());
		}

		return alarm;
	}

}
