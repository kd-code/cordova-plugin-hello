package com.smgroup;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Toast;

public class PrayerTimesNotification extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        if (action.equals("init"))
        {
            
        	JSONObject reader = new JSONObject(data.getString(0));
        	
        	Toast toast = Toast.makeText(this.cordova.getActivity().getApplicationContext(),reader.getString("text"), Toast.LENGTH_LONG);
            toast.show();
            //save data in shared prefs
            init();
            return true;
        }
        else
        {
            return false;
        }
    }
    private void init()
    {
    	//clear previous notifications
    	//read data from shared prefs
    	//set notification for the next 48 hours
    }
    /*todo
     * parse json and fill the data in the shared prefs
     * write boot alarm receiver
     * call init on boot
     * write azan time alarm receiver
     */
    
}

