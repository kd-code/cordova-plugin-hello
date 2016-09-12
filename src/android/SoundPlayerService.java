package com.smgroup;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;

public class SoundPlayerService extends Service
{
	MediaPlayer mp = new MediaPlayer();
	@Override
	public IBinder onBind(Intent arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		
    	AssetFileDescriptor desc;
		try {
			desc = this.getApplicationContext().getAssets().openFd("www/sound.mp3");
	    	mp.setDataSource(desc.getFileDescriptor(),desc.getStartOffset(),desc.getLength());
	    	mp.prepare();
	    	mp.setLooping(false);
	    	mp.start();
	    	mp.setOnCompletionListener(new OnCompletionListener()
	    	{
	            @Override
	            public void onCompletion(MediaPlayer mp)
	            {
	                stopSelf();
	            }
	            });
		}	
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return START_NOT_STICKY;
	}
}