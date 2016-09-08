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

        if (action.equals("init"))
        {
            
        	//save input to shared perfrences
        	SharedPreferences prefs = this.cordova.getActivity().getSharedPreferences("azanplugin", Context.MODE_PRIVATE);
        	prefs.edit().putString("input_json", data.getString(0));
        	prefs.edit().apply();
        	
        	init();
        	
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
    private void init() throws JSONException
    {
    	//clear previous notifications
    	
    	
    	//read data from shared prefs
    	Log.d(TAG, "=========Entered Init=======");
    	
    	SharedPreferences prefs = this.cordova.getActivity().getSharedPreferences("azanplugin", Context.MODE_PRIVATE);
    	String input = prefs.getString("input_json", "");//second arg is the default value
    	
    	Log.d(TAG, input);
    	
    	JSONObject reader = new JSONObject(input);
    	Toast toast = Toast.makeText(this.cordova.getActivity().getApplicationContext(),reader.getString("text"), Toast.LENGTH_LONG);
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

