package com.smgroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;
import java.util.zip.CRC32;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

public class PrayerTimesNotification extends CordovaPlugin {

	public static String TAG = "==kdcode==";
	@Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

		Log.d(TAG, "=========Entered execute=======");
		if (action.equals("init"))
        {
            
			Log.d(TAG, "=========INTO THE IF=======");
			//save input to shared perfrences
        	SharedPreferences prefs = this.cordova.getActivity().getApplicationContext().getSharedPreferences("azanplugin", Context.MODE_PRIVATE);
        	prefs.edit().putString("azan_plugin_input_json", data.getString(0));
        	prefs.edit().commit();
        	init(data.getString(0),this.cordova.getActivity().getApplicationContext());
            return true;
        }
        else
        {
            return false;
        }
    }
    public static void init(String input,Context ctx) throws JSONException
    {
    	//read data 
    	cancelAll(ctx);
    	JSONObject reader = new JSONObject(input);
    	double lat = reader.getDouble("lat");
    	double lng = reader.getDouble("lng");
    	double timezone = reader.getDouble("timezone");
    	String method = reader.getString("method");
    	
    	PrayTime prayers= new PrayTime();
    	prayers.setTimeFormat(prayers.Time24);
    	prayers.setCalcMethod(calcMethodStringToInt(method));
    	prayers.setAsrJuristic(prayers.Shafii);
    	prayers.setAdjustHighLats(prayers.AngleBased);
    	
    	Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        //cal.add(Calendar.DATE, 1);//add 1 day
        
    	
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",Locale.US);
        String str = sdf.format(new Date());
        
        int now_hour = Integer.valueOf(str.split(":")[0]);
        int now_min = Integer.valueOf(str.split(":")[1]);
        
        
        ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal,lat, lng, timezone);
        ArrayList<String> prayerNames = prayers.getTimeNames();
    	
        for (int i = 0; i < prayerTimes.size(); i++)
        {
            String name = prayerNames.get(i);
            if(reader.has(name) || reader.has(name.toLowerCase(Locale.getDefault())))
            {
            	String time = prayerTimes.get(i);
                int hour = Integer.valueOf(time.split(":")[0]);
                int min = Integer.valueOf(time.split(":")[1]);
                 
                if(hour>now_hour || (hour==now_hour && min>now_min))//if pray time is not passed
                {
                	long time_diff = ((hour-now_hour)*60 + min-now_min)*60*1000;//in millis
                	SetAlarmFor(ctx,prayerNames.get(i),System.currentTimeMillis()+time_diff,i);	
                }
            }
            Log.d(TAG,prayerNames.get(i) + " - " + prayerTimes.get(i));
        }
        //set for tommorow
        cal.add(Calendar.DATE, 1);//add 1 day
        ArrayList<String> prayerTimesTomorrow = prayers.getPrayerTimes(cal,lat, lng, timezone);
        for (int i = 0; i < prayerTimesTomorrow.size(); i++)
        {
            String name = prayerNames.get(i);
            if(reader.has(name) || reader.has(name.toLowerCase(Locale.getDefault())))
            {
            	String time = prayerTimesTomorrow.get(i);
                int hour = Integer.valueOf(time.split(":")[0]);
                int min = Integer.valueOf(time.split(":")[1]);
            	long time_diff = (((hour+24)-now_hour)*60 + min-now_min)*60*1000;//in millis
            	SetAlarmFor(ctx,prayerNames.get(i),System.currentTimeMillis()+time_diff,i+100);//i+100 is to assing different request code for tomorrow alarms	
            }
        }
    }
    private static void cancelAll(Context ctx)
    {
    	for(int i=0;i<10;i++)
    	{
	    	Intent alarm_intent = new Intent(ctx,AlarmReceiver.class);
	    	AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
	    	PendingIntent pi = PendingIntent.getBroadcast(ctx, i, alarm_intent, 0);
	    	PendingIntent pi_tomorrow = PendingIntent.getBroadcast(ctx, 100+ i, alarm_intent, 0);
	    	alarmManager.cancel(pi);
	    	alarmManager.cancel(pi_tomorrow);
	    	pi.cancel();
	    	pi_tomorrow.cancel();
    	}
    }
    private static int calcMethodStringToInt(String calc_method)
    {
    	calc_method = calc_method.toLowerCase();
    	PrayTime pr = new PrayTime();
    	if(calc_method=="jafari")
    		return pr.Jafari;
    	if(calc_method=="karachi")
    		return pr.Karachi;
    	if(calc_method=="isna")
    		return pr.ISNA;
    	if(calc_method=="mwl")
    		return pr.MWL;
    	if(calc_method=="makkah")
    		return pr.Makkah;
    	if(calc_method=="egypt")
    		return pr.Egypt;
    	if(calc_method=="tehran")
    		return pr.Tehran;
    	return pr.Tehran;
    }
    private static void SetAlarmFor(Context ctx,String timeName,long time,int requestCode)
    {
    	//cancel previous alarms is done implicitly when scheduling a pendingIntent with same intent and requestCode
    	Intent alarm_intent = new Intent(ctx,AlarmReceiver.class);
    	alarm_intent.putExtra("time_name", timeName);
    	AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
    	//alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(ctx, requestCode, alarm_intent, 0));
    	alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+requestCode * 2000, PendingIntent.getBroadcast(ctx, requestCode, alarm_intent, 0));
    }
    /*todo
     * parse json and fill the data in the shared prefs
     * write boot alarm receiver
     * call init on boot
     * write azan time alarm receiver
     */
}

