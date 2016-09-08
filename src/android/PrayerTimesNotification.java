package com.smgroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class PrayerTimesNotification extends CordovaPlugin {

	private static String TAG = "==kdcode==";
	@Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

		Log.d(TAG, "=========Entered execute=======");
		if (action.equals("init"))
        {
            
			Log.d(TAG, "=========INTO THE IF=======");
			//save input to shared perfrences
        	SharedPreferences prefs = this.cordova.getActivity().getSharedPreferences("azanplugin", Context.MODE_PRIVATE);
        	prefs.edit().putString("input_json", data.getString(0));
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
    	//clear previous notifications
    
    	
    	//read data from shared prefs
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
        
    	
        ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal,
                lat, lng, timezone);
        ArrayList<String> prayerNames = prayers.getTimeNames();
    	
        for (int i = 0; i < prayerTimes.size(); i++)
        {
            Log.d(TAG,prayerNames.get(i) + " - " + prayerTimes.get(i));
        }
		
    	Toast toast = Toast.makeText(ctx,reader.getString("text"), Toast.LENGTH_LONG);
        toast.show();
    	//set notification for the next 48 hours
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
    /*todo
     * parse json and fill the data in the shared prefs
     * write boot alarm receiver
     * call init on boot
     * write azan time alarm receiver
     */
}

