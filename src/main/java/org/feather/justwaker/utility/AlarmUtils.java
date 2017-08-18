package org.feather.justwaker.utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.feather.justwaker.activity.AddAlarmActivity;
import org.feather.justwaker.listeners.AlarmManagerBroadcastReceiver;
import org.feather.justwaker.model.AlarmModel;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class AlarmUtils {

	private static final String sTagAlarms = ":alarms";

	public static final String ALARM_LABEL = "ALARM_LABEL";
	public static final String ALARM_PHRASE = "ALARM_PHRASE";

	public static void addAlarm(Context context, AlarmModel alarm) {

		prepareAlarm(context, alarm);

		saveAlarmInPreferences(context, alarm);
		addSystemAlarms(context, alarm);
	}

	public static void updateAlarm(Context context, AlarmModel alarm) {

		cancelAlarm(context, alarm);

		saveAlarmInPreferences(context, alarm);
		addSystemAlarms(context, alarm);
	}

	private static AlarmModel prepareAlarm(Context context, AlarmModel alarm) {

		List<AlarmModel> alarms = getAlarms(context);
		int notificationId = alarms.size() * 10 + 1;

		alarm.setId(notificationId);

		return alarm;
	}

	private static void addSystemAlarms(Context context, AlarmModel alarm) {

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		int counter = alarm.getId();
		for(Calendar calendar : alarm.getCalendars()) {
			addSystemAlarm(context, alarmManager,
			alarm, ++counter, calendar);
		}
	}

	private static void addSystemAlarm(Context context, AlarmManager alarmManager,
			AlarmModel alarm, int id, Calendar calendar) {

		Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);

		intent.putExtra(ALARM_LABEL, alarm.getLabel());
		intent.putExtra(ALARM_PHRASE, alarm.getPhrase());
		intent.putStringArrayListExtra(AddAlarmActivity.SELECTED_DATES_PARAMETER,
				(ArrayList<String>) alarm.getDatesToIgnore());

		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent,
				PendingIntent.FLAG_CANCEL_CURRENT);

		if(alarm.isWeekly()) {
			long weekInterval= 7 * AlarmManager.INTERVAL_DAY;
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), weekInterval, pendingIntent);
		} else {
			alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
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
			jo.put("label", alarm.getLabel());
			jo.put("phrase", alarm.getPhrase());
			jo.put("is_weekly", alarm.isWeekly());

			jo.put("dates_to_ignore", toJsonArray(alarm.getDatesToIgnore()));
			jo.put("week_days", toJsonArray(alarm.getDaysOfWeek()));
			jo.put("dates_to_alarm", toJsonArray(alarm.getDatesToAlarm()));

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
			alarm.setLabel(jo.getString("label"));
			alarm.setPhrase(jo.getString("phrase"));
			alarm.setWeekly(jo.getBoolean("is_weekly"));

			alarm.setDatesToIgnore(fromStringJsonArray(jo.getJSONArray("dates_to_ignore")));
			alarm.setDaysOfWeek(fromIntegerJsonArray(jo.getJSONArray("week_days")));
			alarm.setRawDatesToAlarm(fromStringJsonArray(jo.getJSONArray("dates_to_alarm")));

		} catch (JSONException e) {
			e.printStackTrace();
			Log.e("Convert Alarm to json", "Save error: " + e.getMessage());
		}

		return alarm;
	}

	protected static JSONArray toJsonArray(List data) {
		JSONArray array = new JSONArray();
		for (Object obj : data) {
			array.put(obj);
		}
		return array;
	}

	private static List<String> fromStringJsonArray(JSONArray array) throws JSONException {
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < array.length(); i++) {
			String str = array.getString(i);
			result.add(str);
		}
		return result;
	}

	private static List<Integer> fromIntegerJsonArray(JSONArray array) throws JSONException {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < array.length(); i++) {
			int obj = array.getInt(i);
			result.add(obj);
		}
		return result;
	}

}
