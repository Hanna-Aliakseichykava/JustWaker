package org.feather.justwaker.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.feather.justwaker.R;

public abstract class BaseMenuActivity extends Activity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_about:
			Log.i("TTS", "About activity is selected");
			showAbout(null);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void showAbout(View view) {
		Intent intent = new Intent(BaseMenuActivity.this, AboutActivity.class);
		startActivity(intent);
	}
}
