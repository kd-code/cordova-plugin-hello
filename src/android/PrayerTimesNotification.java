package com.smgroup;

import org.apache.cordova.*;
import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import android.widget.Toast;

public class PrayerTimesNotification extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        if (action.equals("init"))
        {
            
        	Toast toast = Toast.makeText(this.cordova.getActivity().getApplicationContext(),data.getString(0)+"here is the data", Toast.LENGTH_LONG);
            toast.show();
            return true;
        }
        else
        {
            return false;
        }
    }
}
