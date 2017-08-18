package org.feather.justwaker.utility;

import java.util.ArrayList;
import java.util.Arrays;

import org.feather.justwaker.services.TextToSpeechService;

import android.content.Context;
import android.content.Intent;

import android.util.Log;

public class SpeechUtility {

	public final static String TAG = "SpeechUtility";

	public static void speak(Context context, String msgToRead) {
		speak(context, new ArrayList(Arrays.asList(new String [] {msgToRead})));
	}

	public static void speak(Context context, ArrayList<String> msgToRead) {
		Log.i(TAG, "Texts to read: " + msgToRead);
		Intent speechIntent = new Intent(context, TextToSpeechService.class);
		speechIntent.putStringArrayListExtra(TextToSpeechService.TEXT_TO_READ, msgToRead);
		context.startService(speechIntent);
	}
}
