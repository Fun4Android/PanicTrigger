package org.onpanic.panictrigger.receivers;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.onpanic.panictrigger.R;

public class PasswordFailsReceiver extends DeviceAdminReceiver {
    public static Boolean RUN_PANIC_ACTION = false;
    private static int fails = 0;

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        super.onPasswordFailed(context, intent);
        fails += 1;
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        super.onPasswordSucceeded(context, intent);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean loginActionEnabeld = prefs.getBoolean(context.getString(R.string.pref_login_action), false);
        int failuresNumber = Integer.parseInt(prefs.getString(context.getString(R.string.pref_login_failures_number), "2"));

        if (loginActionEnabeld && (failuresNumber > 0) && (fails >= failuresNumber)) {
            RUN_PANIC_ACTION = true;
        }

        fails = 0;
    }
}
