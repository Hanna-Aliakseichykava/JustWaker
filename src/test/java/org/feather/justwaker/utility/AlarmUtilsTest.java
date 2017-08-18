package org.feather.justwaker.utility;

import org.json.JSONArray;
import org.json.JSONObject;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.feather.justwaker.model.AlarmModel;

import java.util.Calendar;
import java.util.List;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 23)
public class AlarmUtilsTest {

    int notificationId = 1;

    List<Integer> daysOfWeek = Arrays.asList(new Integer [] {1, 2});
    String expectedDaysOfWeekJson = "[1,2]";

    List<String> datesToAlarm = Arrays.asList(new String [] {"23-10-2017 18:35", "24-10-2017 18:35"});
    String expectedDatesToAlarmJson = "[\"23-10-2017 18:35\",\"24-10-2017 18:35\"]";

    boolean isWeekly = true;
    String label = "Label 1";
    String phrase = "Phrase 1";
    List<String> datesToIgnore = Arrays.asList(new String [] {"25-11-2017", "26-12-2017"});
    String expectedDatesToIgnoreJson = "[\"25-11-2017\",\"26-12-2017\"]";

    @Test
    public void testConvertToJsonObject() throws Exception {

        AlarmModel alarm = new AlarmModel();
        alarm.setId(notificationId);
        alarm.setDaysOfWeek(daysOfWeek);
        alarm.setRawDatesToAlarm(datesToAlarm);

        alarm.setLabel(label);
        alarm.setPhrase(phrase);
        alarm.setWeekly(isWeekly);
        alarm.setDatesToIgnore(datesToIgnore);

        JSONObject jo = AlarmUtils.convertToJsonObject(alarm);

        assertEquals(notificationId, jo.getInt("id"));
        assertEquals(label, jo.getString("label"));
        assertEquals(phrase, jo.getString("phrase"));
        assertEquals(isWeekly, jo.getBoolean("is_weekly"));
        assertEquals(expectedDatesToIgnoreJson, jo.getJSONArray("dates_to_ignore").toString());
        assertEquals(expectedDaysOfWeekJson, jo.getJSONArray("week_days").toString());
        assertEquals(expectedDatesToAlarmJson, jo.getJSONArray("dates_to_alarm").toString());
    }

    @Test
    public void testParseJsonObjectToAlarm() throws Exception {
        JSONObject jo = new JSONObject();

        jo.put("id", notificationId);

        jo.put("label", label);
        jo.put("phrase", phrase);
        jo.put("is_weekly", isWeekly);

        jo.put("dates_to_ignore", AlarmUtils.toJsonArray(datesToIgnore));
        jo.put("week_days", AlarmUtils.toJsonArray(daysOfWeek));
        jo.put("dates_to_alarm", AlarmUtils.toJsonArray(datesToAlarm));



        AlarmModel alarm = AlarmUtils.parseJsonObjectToAlarm(jo);

        assertEquals(notificationId, alarm.getId());
        assertEquals(label, alarm.getLabel());
        assertEquals(phrase, alarm.getPhrase());
        assertEquals(isWeekly, alarm.isWeekly());
        assertEquals(datesToIgnore, alarm.getDatesToIgnore());
        assertEquals(daysOfWeek, alarm.getDaysOfWeek());
        assertEquals(datesToAlarm, alarm.getDatesToAlarm());
    }
}
