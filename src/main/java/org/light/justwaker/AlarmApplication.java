package org.light.justwaker;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.acra.*;
import org.acra.annotation.*;
import org.acra.config.*;

/**
 * Created by ANNA on 12.08.2017.
 */

@ReportsCrashes(
)
public class AlarmApplication extends Application {

	private final static String TAG = "ACRA: ";

	@Override
	protected void attachBaseContext(Context context) {
		super.attachBaseContext(context);

		ConfigurationBuilder builder = new ConfigurationBuilder(this);
		builder.setMailTo(context.getString(R.string.contacts_email));

		try {
			ACRA.init(this, builder.build());

		} catch (ACRAConfigurationException e) {
			Log.e(TAG, e.getStackTrace().toString());
			throw new RuntimeException(TAG + e.getMessage());
		}
	}
}
