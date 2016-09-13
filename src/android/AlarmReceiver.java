package com.smgroup;

import org.json.JSONException;
import org.json.JSONObject;

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
		String title = "";
		String sound = "";
		String time_name = intent.getExtras().getString("time_name");
		try
		{
			JSONObject reader = new JSONObject(data);
			if(reader.has(time_name))
			{
				title = reader.getJSONObject(time_name).getString("text");
				sound = reader.getJSONObject(time_name).getString("mp3");
			}
			else if(reader.has(time_name.toLowerCase()))
			{
				title = reader.getJSONObject(time_name.toLowerCase()).getString("text");
				sound = reader.getJSONObject(time_name.toLowerCase()).getString("mp3");
			}
			else
			{
				return;//nothign to do
			}
			
		}
		catch (JSONException e)
		{
			e.printStackTrace();
			Log.d(PrayerTimesNotification.TAG,e.getLocalizedMessage());
		}
		
    			
		Toast.makeText(context, "recieved title="+title +" and sound ="+sound , Toast.LENGTH_LONG).show();
		Log.d(PrayerTimesNotification.TAG,"recieved");
    	Log.d(PrayerTimesNotification.TAG,data);
    	Log.d(PrayerTimesNotification.TAG,"time is: " + intent.getExtras().getString("time_name"));
    	
    	
    	
    	Intent play_intent = new Intent(context.getApplicationContext(), SoundPlayerService.class);
    	play_intent.putExtra("do", "play");
    	play_intent.putExtra("sound", sound);
    	context.getApplicationContext().startService(play_intent);
    	
    	
    	Intent i = new Intent(context.getApplicationContext(), SoundPlayerService.class);
    	i.putExtra("do", "stop");
		PendingIntent contentIntent = PendingIntent.getService(context.getApplicationContext(), 0, i, 0);
		NotificationCompat.Builder b = new NotificationCompat.Builder(context.getApplicationContext());

		b.setAutoCancel(true)
				.setDefaults(Notification.DEFAULT_LIGHTS)
				.setWhen(System.currentTimeMillis())
				.setSmallIcon(context.getApplicationContext().getResources().getIdentifier("icon", "drawable", context.getApplicationContext().getPackageName()))
				.setContentTitle(title)
				.setContentText("")
				.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
				.setContentIntent(contentIntent)
				.setDeleteIntent(contentIntent);
				
				
		
		NotificationManager notificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(1, b.build());
	}  
}
