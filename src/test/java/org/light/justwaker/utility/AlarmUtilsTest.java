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

@RunWith(RobolectricTestRunner.class)
//@Config(manifest = Config.NONE, sdk = 23)
public class AlarmUtilsTest {

    int notificationId = 1;
    String dateTime = "24-10-2017 18:35";
    boolean isWeekly = true;
    String label = "Label 1";
    String phrase = "Phrase 1";
    List<String> datesToIgnore = Arrays.asList(new String [] {"25-11-2017", "26-12-2017"});

    @Test
    public void testConvertToJsonObject() throws Exception {

        Calendar calendar = DateTimeUtility.calendarFromDateTime(dateTime);

        AlarmModel alarm = new AlarmModel(notificationId, calendar, label, phrase, isWeekly, datesToIgnore);
        JSONObject jo = AlarmUtils.convertToJsonObject(alarm);

        assertEquals(jo.getInt("id"), notificationId);
        assertEquals(jo.getString("date_time"), dateTime);
        assertEquals(jo.getString("label"), label);
        assertEquals(jo.getString("phrase"), phrase);
        assertEquals(jo.getBoolean("is_weekly"), isWeekly);
        assertEquals(jo.getJSONArray("dates_to_ignore").toString(), datesToIgnore.toString());
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

        assertEquals(alarm.getId(), notificationId);
        assertEquals(alarm.getDateTime(), dateTime);
        assertEquals(alarm.getLabel(), label);
        assertEquals(alarm.getPhrase(), phrase);
        assertEquals(alarm.isWeekly(), isWeekly);
        assertEquals(alarm.getDatesToIgnore(), datesToIgnore);
    }
}
