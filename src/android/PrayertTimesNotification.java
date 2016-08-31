package com.example.plugin;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import android.widget.Toast;

public class PrayertTimesNotification extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        if (action.equals("init"))
        {
            Toast toast = Toast.makeText(callbackContext,"here is the data:"+data.getString(0);, Toast.LENGTH_LONG);
            toast.show();
        }
        else
        {
            return false;
        }
    }
}
