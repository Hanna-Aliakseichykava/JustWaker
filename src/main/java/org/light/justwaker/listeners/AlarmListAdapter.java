package org.light.justwaker.listeners;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.light.justwaker.AlarmsListActivity;
import org.light.justwaker.R;
import org.light.justwaker.model.AlarmModel;
import org.light.justwaker.utility.AlarmUtils;

public class AlarmListAdapter extends BaseAdapter {

	private static final String TAG = AlarmListAdapter.class.getSimpleName();
	List<AlarmModel> listArray;

	AlarmsListActivity parentActivity;
	LayoutInflater inflater;

	public AlarmListAdapter(AlarmsListActivity parentActivity, List<AlarmModel> listArray) {
		this.parentActivity = parentActivity;
		this.listArray = new ArrayList<>(listArray);
		inflater = (LayoutInflater) parentActivity
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		Log.i(TAG, "List Items Count: " + listArray.size());
		return listArray.size();
	}

	@Override
	public Object getItem(int i) {
		return listArray.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int index, View view, final ViewGroup parent) {

		if (view == null) {
			view = inflater.inflate(R.layout.single_list_item, parent, false);
		}

		final AlarmModel dataModel = listArray.get(index);
		final String label = dataModel.getInfo(view.getResources());

		TextView textView = (TextView) view.findViewById(R.id.tv_string_data);
		textView.setText(label);

		Button btnEdit = (Button) view.findViewById(R.id.btn_edit);
		btnEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.i(TAG, "OnClick Edit for Alarm: " + label);
				parentActivity.onClickEditAlarmButton(dataModel);
			}
		});

		Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.i(TAG, "OnClick Cancel for Alarm: " + label);
				AlarmUtils.cancelAlarm(parentActivity, dataModel);
				parentActivity.updateListView();
				Toast.makeText(parent.getContext(), "Alarm is cancelled", Toast.LENGTH_SHORT).show();
			}
		});

		return view;
	}
}
