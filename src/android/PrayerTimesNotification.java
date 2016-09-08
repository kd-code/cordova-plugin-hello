package com.smgroup;

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
        	
        	/*JSONObject reader = new JSONObject(data.getString(0));
        	
        	Toast toast = Toast.makeText(this.cordova.getActivity().getApplicationContext(),reader.getString("text"), Toast.LENGTH_LONG);
            toast.show();*/
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
		//SharedPreferences prefs = ctx.getSharedPreferences("azanplugin", Context.MODE_PRIVATE);
		//input = prefs.getString("input_json", "");//second arg is the default value
		//Log.d(TAG, "input is [" +  input+"]");
		JSONObject reader = new JSONObject(input);
    	Toast toast = Toast.makeText(ctx,reader.getString("text"), Toast.LENGTH_LONG);
        toast.show();
    	//set notification for the next 48 hours
    }
    /*todo
     * parse json and fill the data in the shared prefs
     * write boot alarm receiver
     * call init on boot
     * write azan time alarm receiver
     */
    
}

