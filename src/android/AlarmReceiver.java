package com.smgroup;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver
{
	@Override  
	public void onReceive(Context context, Intent intent)
	{  
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
		String data = prefs.getString("azan_plugin_input_json", "NO_VAL");
		Toast.makeText(context, "recieved", Toast.LENGTH_LONG).show();
		Log.d(PrayerTimesNotification.TAG,"recieved");
    	Log.d(PrayerTimesNotification.TAG,data);
    	Log.d(PrayerTimesNotification.TAG,"time is: " + intent.getExtras().getString("time_name"));
    	
    	Intent play_intent = new Intent(context.getApplicationContext(), SoundPlayerService.class);
    	play_intent.setAction("play");
    	context.getApplicationContext().startService(play_intent);
    	
    	
    	Intent i = new Intent(context.getApplicationContext(), SoundPlayerService.class);
    	i.setAction("stop");
		PendingIntent contentIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, i, 0);
		NotificationCompat.Builder b = new NotificationCompat.Builder(context.getApplicationContext());

		b.setAutoCancel(true)
				.setDefaults(Notification.DEFAULT_ALL)
				.setWhen(System.currentTimeMillis())
				.setSmallIcon(context.getApplicationContext().getResources().getIdentifier("icon", "drawable", context.getApplicationContext().getPackageName()))
				.setContentTitle("title")
				.setContentText("text")
				.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
				.setContentIntent(contentIntent)
				.setDeleteIntent(contentIntent)
				.setContentInfo("Info");
		
		NotificationManager notificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(1, b.build());
	}  
}
