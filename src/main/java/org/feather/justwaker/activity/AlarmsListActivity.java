package org.feather.justwaker.activity;

import java.util.Collections;
import java.util.List;

import org.feather.justwaker.R;
import org.feather.justwaker.listeners.AlarmListAdapter;
import org.feather.justwaker.model.AlarmModel;
import org.feather.justwaker.utility.AlarmUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.util.Log;

public class AlarmsListActivity extends BaseMenuActivity {

	private static final String TAG = AlarmsListActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_alarms_list);

		updateListView();
	}

	@Override
	protected void onResume() {
		super.onResume();

		updateListView();
	}

	public void onClickAddAlarmButton(View view) {
		Intent intent = new Intent(AlarmsListActivity.this, AddAlarmActivity.class);
		startActivity(intent);
	}

	public void onClickEditAlarmButton(AlarmModel alarm) {
		Intent intent = new Intent(AlarmsListActivity.this, EditAlarmActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt(EditAlarmActivity.ALARM_ID, alarm.getId());
		intent.putExtras(bundle);

		startActivity(intent);
	}

	public void cancelAllAlarms(View view) {
		Context context = this.getApplicationContext();
		AlarmUtils.cancelAllAlarms(context);
		updateListView();
	}

	public void updateListView() {

		AlarmListAdapter adapter = new AlarmListAdapter(this, getListItems());

		ListView listView = (ListView)findViewById(R.id.alarmsListView);
		listView.setAdapter(adapter);
	}

	private List<AlarmModel> getListItems() {
		List<AlarmModel> alarms = AlarmUtils.getAlarms(this);
		Collections.sort(alarms);
		Log.i(TAG, "Alarms count: " + alarms.size());
		return alarms;
	}
}