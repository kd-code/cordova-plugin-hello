package com.smgroup.PrayertTimesNotification;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import android.widget.Toast;

public class PrayertTimesNotification extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        if (action.equals("init"))
        {
            Toast toast = Toast.makeText(this.cordova.getActivity().getApplicationContext(),"here is the data", Toast.LENGTH_LONG);
            toast.show();
            return true;
        }
        else
        {
            return false;
        }
    }
}
