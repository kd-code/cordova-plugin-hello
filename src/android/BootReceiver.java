package com.smgroup;

import org.json.JSONException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
public class BootReceiver extends BroadcastReceiver
{
	@Override
    public void onReceive(Context context, Intent intent)
	{
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
		String data = prefs.getString("azan_plugin_input_json", "NO_VAL");
		try {
			PrayerTimesNotification.init(data, context.getApplicationContext());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
