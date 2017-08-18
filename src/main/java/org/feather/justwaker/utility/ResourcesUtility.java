package org.feather.justwaker.utility;

import org.feather.justwaker.R;

import java.util.Arrays;
import java.util.List;

import android.content.res.Resources;

/**
 * Created by ANNA on 06.08.2017.
 */

public class ResourcesUtility {

	private static List<String> daysShortCodes = null;

	public static List<String> getDaysShortCodes(Resources resources) {
		if(daysShortCodes == null) {
			daysShortCodes = Arrays.asList(resources.
					getStringArray(R.array.days_of_week_abbreviations));
		}
		return daysShortCodes;
	}
}
