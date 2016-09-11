package com.smgroup;

import java.io.IOException;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
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
    	
    	NotificationManager manager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
    	NotificationCompat.Builder b = new NotificationCompat.Builder(context);
    	b.setDefaults(Notification.DEFAULT_ALL).setWhen(System.currentTimeMillis());
    	int nid = 123456789;
    	Notification note = b.build();
    	note.sound = Uri.parse("file:///android_asset/www/sound.mp3");
    	manager.notify(nid, note);
    	/*
    	AssetFileDescriptor desc;
		try {
			desc = context.getApplicationContext().getAssets().openFd("www/sound.mp3");
			MediaPlayer mp = new MediaPlayer();
	    	mp.setDataSource(desc.getFileDescriptor(),desc.getStartOffset(),desc.getLength());
	    	mp.prepare();
	    	mp.setLooping(false);
	    	mp.start();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	
	}  
}
