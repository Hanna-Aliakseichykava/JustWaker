package org.feather.justwaker.activity;

import org.feather.justwaker.R;
import org.feather.justwaker.utility.AlarmUtils;
import org.feather.justwaker.utility.SpeechUtility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class WakeUpActivity extends Activity {

	private static final String TAG = "WakeUpActivity";

	private static final int TIMEOUT = 10000;

	boolean isActivityClosed = false;

	private String alarmName;
	private String phrase;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wake_up);

		Intent intent = this.getIntent();

		Bundle extras = intent.getExtras();

		if (extras != null && extras.containsKey(AlarmUtils.ALARM_PHRASE)) {
			alarmName = extras.getString(AlarmUtils.ALARM_LABEL);
			phrase = extras.getString(AlarmUtils.ALARM_PHRASE);
		} else {
			alarmName = "Not Found";
			phrase = "Not Found";
			Log.e(TAG, "Phrase is not found by");
		}

		TextView alarmLabel = (TextView)findViewById(R.id.alarm_label);
		alarmLabel.setText(alarmName);

		TextView wakeUpLabel = (TextView)findViewById(R.id.wake_up_text_phrase);
		wakeUpLabel.setText(phrase);
	}

	@Override
	protected void onStart() {
		super.onStart();

		final Context context = this;

		final Handler handler = new Handler(Looper.getMainLooper());

		Runnable runable = new Runnable() {

			@Override 
			public void run() {
				try{
					if(!isActivityClosed) {
						try {
							Log.i(TAG, "Notify");
							inform(context, phrase);

							//task to be done again
							handler.postDelayed(this, TIMEOUT);
						} catch (Exception e) {
							e.printStackTrace();
							Log.e(TAG, e.getMessage());
						}
					}
				}
				catch (Exception e) {
					e.printStackTrace();
					Log.e(TAG, e.getMessage());
				}
			}
		};

		handler.postDelayed(runable, 2000);
	}

	private void inform(Context context, String phrase) {
		Toast.makeText(context.getApplicationContext(), phrase, Toast.LENGTH_LONG).show();
		SpeechUtility.speak(context, phrase);

	}

	public void closeWindow(View view) {
		onBackPressed();
	}

	@Override
	public void onBackPressed() {
		isActivityClosed = true;
		Looper.getMainLooper().quit();
		super.onBackPressed();
	}
}
