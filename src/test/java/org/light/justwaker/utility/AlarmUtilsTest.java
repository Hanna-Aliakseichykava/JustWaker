package org.light.justwaker.utility;

import org.json.JSONArray;
import org.json.JSONObject;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.light.justwaker.model.AlarmModel;

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
    String dateTime = "24-10-2017 18:35";
    boolean isWeekly = true;
    String label = "Label 1";
    String phrase = "Phrase 1";
    List<String> datesToIgnore = Arrays.asList(new String [] {"25-11-2017", "26-12-2017"});
    String expectedDatesToIgnoreJson = "[\"25-11-2017\",\"26-12-2017\"]";

    @Test
    public void testConvertToJsonObject() throws Exception {

        Calendar calendar = DateTimeUtility.calendarFromDateTime(dateTime);

        AlarmModel alarm = new AlarmModel(notificationId, calendar, label, phrase, isWeekly, datesToIgnore);
        JSONObject jo = AlarmUtils.convertToJsonObject(alarm);

        assertEquals(notificationId, jo.getInt("id"));
        assertEquals(dateTime, jo.getString("date_time"));
        assertEquals(label, jo.getString("label"));
        assertEquals(phrase, jo.getString("phrase"));
        assertEquals(isWeekly, jo.getBoolean("is_weekly"));
        assertEquals(expectedDatesToIgnoreJson, jo.getJSONArray("dates_to_ignore").toString());
    }

    @Test
    public void testParseJsonObjectToAlarm() throws Exception {
        JSONObject jo = new JSONObject();

        jo.put("id", notificationId);
        jo.put("date_time", dateTime);
        jo.put("label", label);
        jo.put("phrase", phrase);
        jo.put("is_weekly", isWeekly);

        JSONArray array = new JSONArray();
        for (String str : datesToIgnore) {
             array.put(str);
        }
        jo.put("dates_to_ignore", array);



        AlarmModel alarm = AlarmUtils.parseJsonObjectToAlarm(jo);

        assertEquals(notificationId, alarm.getId());
        assertEquals(dateTime, alarm.getDateTime());
        assertEquals(label, alarm.getLabel());
        assertEquals(phrase, alarm.getPhrase());
        assertEquals(isWeekly, alarm.isWeekly());
        assertEquals(datesToIgnore, alarm.getDatesToIgnore());
    }
}
