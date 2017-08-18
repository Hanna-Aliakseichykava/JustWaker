package org.feather.justwaker.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import org.feather.justwaker.R;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_about);
	}

	public void onClickBackButton(View view) {
		super.onBackPressed();
	}
}